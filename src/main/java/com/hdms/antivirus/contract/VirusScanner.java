package com.hdms.antivirus.contract;

import java.io.IOException;
import java.io.InputStream;

public interface VirusScanner {

    /**
     * Input stream parameter are NOT closed inside the method.
     * Streams the given data to the server in chunks. The whole data inputStream not kept in memory.
     * Scans inputStream for virus by passing the InputStream to method
     */
    byte[] scan(InputStream inputStream) throws IOException;

    /**
     * Scans bytes for virus by passing the byte[] to method
     **/
    byte[] scan(byte[] in) throws IOException;

    boolean isCleanReply(byte[] reply);
}
