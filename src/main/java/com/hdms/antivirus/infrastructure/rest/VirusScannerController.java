package com.hdms.antivirus.infrastructure.rest;

import com.hdms.antivirus.contract.VirusScanner;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class VirusScannerController {

    private final VirusScanner virusScanner;

    @RequestMapping(value = "/diagnose", method = RequestMethod.POST)
    public @ResponseBody
    String diagnose(@RequestParam("file") MultipartFile file) throws IOException {

        if (!file.isEmpty ()) {
            byte[] r = virusScanner.scan ( file.getInputStream () );
            return "It is a Good File : " + virusScanner.isCleanReply ( r ) + "\n";
        } else throw new IllegalArgumentException ( "empty file" );
    }


    @RequestMapping(value = "/diagnosticReport", method = RequestMethod.POST)
    public @ResponseBody
    String diagnosticReport(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty ()) {
            return new String ( virusScanner.scan ( file.getInputStream () ) );
        } else throw new IllegalArgumentException ( "empty file" );
    }
}
