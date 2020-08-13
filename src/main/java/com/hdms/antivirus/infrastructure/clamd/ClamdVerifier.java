package com.hdms.antivirus.infrastructure.clamd;

import com.hdms.antivirus.contract.HealthCheck;
import com.hdms.antivirus.infrastructure.clamd.config.ClamdConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClamdVerifier implements HealthCheck {

    private final ClamdConfig clamdConfig;

    /**
     * Run PING command to clamd to test it is responding.
     *
     * @return true if the server responded with proper ping reply.
     */
    @Override
    public boolean ping() throws IOException {
        try (Socket socket = new Socket ( clamdConfig.getHostname (), clamdConfig.getPort () ); OutputStream outs = socket.getOutputStream ()) {
            socket.setSoTimeout ( clamdConfig.getTimeout () );
            outs.write ( "PING".getBytes ( StandardCharsets.US_ASCII ) );
            outs.flush ();
            byte[] b = new byte[4];// PONG
            InputStream inputStream = socket.getInputStream ();
            int copyIndex = 0;
            int readResult;
            do {
                readResult = inputStream.read ( b, copyIndex, Math.max ( b.length - copyIndex, 0 ) );
                copyIndex += readResult;
            } while (readResult > 0);
            return Arrays.equals ( b, "PONG".getBytes ( StandardCharsets.US_ASCII ) );
        }
    }
}
