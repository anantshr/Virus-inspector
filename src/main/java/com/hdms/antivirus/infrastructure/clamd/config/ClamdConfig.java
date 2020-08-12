package com.hdms.antivirus.infrastructure.clamd.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "clamd")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClamdConfig {
    private String hostname;
    private int port;
    private int timeout;
    private String maxfilesize;
    private String maxrequestsize;
}