package com.hdms.antivirus.domain;

import java.io.IOException;

public interface Status {
    boolean ping() throws IOException;
}
