package com.hdms.antivirus.port.clamd;

import com.hdms.antivirus.config.ClamdConfig;
import io.vavr.Tuple2;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * These tests assume clamd is running and responding in the virtual machine.
 */
public class InstreamTest {

    ClamdConfig clamdConfig = new ClamdConfig ("localhost",3310,500);

    private Tuple2<byte[], ClamAVClient> scan(byte[] input) throws IOException {
        ClamAVClient cl = new ClamAVClient( clamdConfig );
        byte[] scan = cl.scan(input);
        return new Tuple2<>(scan, cl);
    }

    @Test
    public void testRandomBytes() throws IOException {
        Tuple2<byte[], ClamAVClient> scanResult = scan("alsdklaksdla".getBytes(StandardCharsets.US_ASCII));
        assertTrue(scanResult._2.isCleanReply(scanResult._1));
    }

    @Test
    public void testPositive() throws IOException {
        // http://www.eicar.org/86-0-Intended-use.html
        byte[] EICAR = "X5O!P%@AP[4\\PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*".getBytes(StandardCharsets.US_ASCII);
        Tuple2<byte[], ClamAVClient> scanResult = scan(EICAR);
        assertFalse(scanResult._2.isCleanReply(scanResult._1));
    }

    @Test
    public void testStreamChunkingWorks() throws IOException {
        byte[] multipleChunks = new byte[50000];
        Tuple2<byte[], ClamAVClient> scanResult = scan(multipleChunks);
        assertTrue(scanResult._2.isCleanReply(scanResult._1));
    }

    @Test
    public void testChunkLimit() throws IOException {
        byte[] maximumChunk = new byte[2048];
        Tuple2<byte[], ClamAVClient> scanResult = scan(maximumChunk);
        assertTrue(scanResult._2.isCleanReply(scanResult._1));
    }

    @Test
    public void testZeroBytes() throws IOException {
        Tuple2<byte[], ClamAVClient> scanResult = scan(new byte[]{});
        assertTrue(scanResult._2.isCleanReply(scanResult._1));
    }

    /*
       //#TODO need to see the stream test properly
    private Tuple2<byte[], ClamAVClient> scan(InputStream input) throws IOException {
        ClamAVClient cl = new ClamAVClient(CLAMAV_HOST, CLAMAV_PORT);
        byte[] scan = cl.scan(input);
        return new Tuple2<>(scan, cl);
    }
    // TODO: Test need to uncomment and see why slow stream test is not working
  @Test(expected = ClamAVSizeLimitException.class)
    public void testSizeLimit() throws UnknownHostException, IOException {
        scan(new SlowInputStream());
    }

     */
}
