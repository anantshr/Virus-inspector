package com.hdms.antivirus.port.clamd;

import com.hdms.antivirus.config.ClamdConfig;
import com.hdms.antivirus.domain.Scanner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Simple client for ClamAV's clamd scanner.
 * Provides straightforward instream scanning.
 */
@Component
@RequiredArgsConstructor
public class ClamAVClient implements Scanner {

    private final ClamdConfig clamdConfig;

    /**
     * Streams the given data to the server in chunks. The whole data is not kept in memory.
     * This method is preferred if you don't want to keep the data in memory, for instance by scanning a file on disk.
     * Since the parameter InputStream is not reset, you can not use the stream afterwards, as it will be left in a EOF-state.
     * If your goal is to scan some data, and then pass that data further, consider using {@link #scan(byte[]) scan(byte[] in)}.
     * <p>
     * Opens a socket and reads the reply. Parameter input stream is NOT closed.
     *
     * @param is data to scan. Not closed by this method!
     * @return server reply
     */
    @Override
    public byte[] scan(InputStream is) throws IOException {
        try (
                Socket socket = new Socket ( clamdConfig.getHostname (), clamdConfig.getPort () );
                OutputStream outs = new BufferedOutputStream ( socket.getOutputStream () )) {
            socket.setSoTimeout ( clamdConfig.getTimeout() );

            // handshake
            outs.write ( "zINSTREAM\0".getBytes ( StandardCharsets.US_ASCII ) );
            outs.flush ();
            // "do not exceed StreamMaxLength as defined in clamd.conf,
            // otherwise clamd will reply with INSTREAM size limit exceeded and close the connection."
            int CHUNK_SIZE = 2048;
            byte[] chunk = new byte[CHUNK_SIZE];

            try (InputStream clamIs = socket.getInputStream ()) {
                // send data
                int read = is.read ( chunk );
                while (read >= 0) {
                    // The format of the chunk is: '<length><data>' where <length> is the size of the following data in bytes expressed as a 4 byte unsigned
                    // integer in network byte order and <data> is the actual chunk. Streaming is terminated by sending a zero-length chunk.
                    byte[] chunkSize = ByteBuffer.allocate ( 4 ).putInt ( read ).array ();

                    outs.write ( chunkSize );
                    outs.write ( chunk, 0, read );
                    if (clamIs.available () > 0) {
                        // reply from server before scan command has been terminated.
                        byte[] reply = assertSizeLimit ( readAll ( clamIs ) );
                        throw new IOException ( "Scan aborted. Reply from server: " + new String ( reply, StandardCharsets.US_ASCII ) );
                    }
                    read = is.read ( chunk );
                }

                // terminate scan
                outs.write ( new byte[]{0, 0, 0, 0} );
                outs.flush ();
                // read reply
                return assertSizeLimit ( readAll ( clamIs ) );
            }
        }
    }

    /**
     * Scans bytes for virus by passing the bytes to clamav
     *
     * @param in data to scan
     * @return server reply
     **/
    @Override
    public byte[] scan(byte[] in) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream ( in );
        return scan ( bis );
    }

    /**
     * Interpret the result from a  ClamAV scan, and determine if the result means the data is clean
     *
     * @param reply The reply from the server after scanning
     * @return true if no virus was found according to the clamd reply message
     */

    @Override
    public boolean isCleanReply(byte[] reply) {
        String r = new String ( reply, StandardCharsets.US_ASCII );
        return (r.contains ( "OK" ) && !r.contains ( "FOUND" ));
    }

    private byte[] assertSizeLimit(byte[] replyByt) {
        String reply = new String ( replyByt, StandardCharsets.US_ASCII );
        if (reply.startsWith ( "INSTREAM size limit exceeded." ))
            throw new ClamAVSizeLimitException ( "Clamd size limit exceeded. Full reply from server: " + reply );
        return replyByt;
    }

    // reads all available bytes from the stream
    private static byte[] readAll(InputStream is) throws IOException {
        try (ByteArrayOutputStream tmp = new ByteArrayOutputStream ()) {
            byte[] buf = new byte[2000];
            int read;
            do {
                read = is.read ( buf );
                tmp.write ( buf, 0, read );
            } while ((read > 0) && (is.available () > 0));
            return tmp.toByteArray ();
        }
    }
}
