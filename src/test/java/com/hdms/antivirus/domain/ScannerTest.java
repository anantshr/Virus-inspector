package com.hdms.antivirus.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ScannerTest {
    @Autowired
    private Scanner scanner;

    @Test
    public void testScanner(){
        try {
            scanner.scan("asdf".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
