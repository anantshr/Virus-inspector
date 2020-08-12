package com.hdms.antivirus.contract;

import java.io.IOException;
import java.io.InputStream;

public interface VirusScanner {

    byte[] scan(InputStream is) throws IOException;
    byte[] scan(byte[] in) throws IOException;
    boolean isCleanReply(byte[] reply);
}
