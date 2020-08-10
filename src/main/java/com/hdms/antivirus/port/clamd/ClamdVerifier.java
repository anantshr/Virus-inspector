package com.hdms.antivirus.port.clamd;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@RequiredArgsConstructor
public class ClamdVerifier {

    private final String hostName;
    private final int port;
    private final int timeout;

    /**
     * Run PING command to clamd to test it is responding.
     *
     * @return true if the server responded with proper ping reply.
     */
    public boolean ping() throws IOException {
        try (Socket socket = new Socket(hostName,port); OutputStream outs = socket.getOutputStream()) {
            socket.setSoTimeout(timeout);
            outs.write("PING".getBytes(StandardCharsets.US_ASCII));
            outs.flush();
            byte[] b = new byte[4];// PONG
            InputStream inputStream = socket.getInputStream();
            int copyIndex = 0;
            int readResult;
            do {
                readResult = inputStream.read(b, copyIndex, Math.max(b.length - copyIndex, 0));
                copyIndex += readResult;
            } while (readResult > 0);
            return Arrays.equals(b, "PONG".getBytes(StandardCharsets.US_ASCII));
        }
    }
}
