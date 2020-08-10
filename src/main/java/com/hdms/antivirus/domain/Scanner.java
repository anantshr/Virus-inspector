package com.hdms.antivirus.domain;

import java.io.IOException;
import java.io.InputStream;

public interface Scanner {

    byte[] scan(InputStream is) throws IOException;
    byte[] scan(byte[] in) throws IOException;
    boolean isCleanReply(byte[] reply);
}
