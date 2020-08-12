package com.hdms.antivirus.domain;

import com.hdms.antivirus.contract.VirusScanner;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class VirusScannerTest {
    @Autowired
    private VirusScanner virusScanner;

    @Test
    public void testScanner(){
        try {
            virusScanner.scan("asdf".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
