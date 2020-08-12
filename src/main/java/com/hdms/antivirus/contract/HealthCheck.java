package com.hdms.antivirus.contract;

import java.io.IOException;

public interface HealthCheck {
    boolean ping() throws IOException;
}
