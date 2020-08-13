package com.hdms.antivirus.infrastructure.rest;

import com.hdms.antivirus.contract.HealthCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
public class HealthCheckController {

    private final HealthCheck healthStatus;

    public HealthCheckController(HealthCheck healthStatus) {
        this.healthStatus = healthStatus;
    }

    @RequestMapping("/")
    public String ping() throws IOException {
        UUID requestId = UUID.randomUUID();
        log.info("[/] [ID:{}]",requestId);
        return "Application is responding: " + healthStatus.ping () + "\n";
    }
}
