package com.hdms.antivirus.infrastructure.rest;

import com.hdms.antivirus.contract.HealthCheck;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HealthCheckController {

    private final HealthCheck healthStatus;

    public HealthCheckController(HealthCheck healthStatus) {
        this.healthStatus = healthStatus;
    }

    @RequestMapping("/")
    public String ping() throws IOException {
        return "Application is responding: " + healthStatus.ping() + "\n";
    }
}
