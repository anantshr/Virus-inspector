package com.hdms.antivirus;

import com.hdms.antivirus.infrastructure.clamd.config.ClamdConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.client.RestTemplate;

import javax.servlet.MultipartConfigElement;

@Configuration
@EnableConfigurationProperties(value = ClamdConfig.class)
@SpringBootApplication
@Slf4j
public class Application {

    private final ClamdConfig clamdConfig;

    public Application(ClamdConfig clamdConfig) {
        this.clamdConfig = clamdConfig;
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory ();
        factory.setMaxFileSize ( DataSize.parse ( clamdConfig.getMaxfilesize () ) );
        factory.setMaxRequestSize ( DataSize.parse ( clamdConfig.getMaxrequestsize () ) );
        return factory.createMultipartConfig ();
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication ( Application.class );
        app.run ( args );
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate ();
    }
}
