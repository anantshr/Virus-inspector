package com.hdms.antivirus.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "clamd")
@Setter
@Getter
public class ClamdConfig {
    private String hostname;
    private int port;
    private int timeout;
    private String maxfilesize;
    private String maxrequestsize;
    public ClamdConfig(){
    }
    public ClamdConfig(String hostname, int port, int timeout) {
        this.hostname = hostname;
        this.port = port;
        this.timeout = timeout;
    }

}
