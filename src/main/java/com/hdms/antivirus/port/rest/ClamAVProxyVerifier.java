package com.hdms.antivirus.port.rest;

import com.hdms.antivirus.domain.Status;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ClamAVProxyVerifier  {

    private final Status status;

    public ClamAVProxyVerifier(Status status) {
        this.status = status;
    }

    /**
     * @return Clamd status.
     */
    @RequestMapping("/")
    public String ping() throws IOException {
        return "Clamd responding: " + status.ping() + "\n";
    }
}
