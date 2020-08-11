package com.hdms.antivirus.domain;

import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

public interface Status {

    @RequestMapping("/")
    String ping() throws IOException;
}
