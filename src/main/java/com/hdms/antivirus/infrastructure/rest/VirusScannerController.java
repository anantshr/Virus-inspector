package com.hdms.antivirus.infrastructure.rest;

import com.hdms.antivirus.contract.VirusScanner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VirusScannerController {

    private final VirusScanner virusScanner;

    @RequestMapping(value = "/diagnose", method = RequestMethod.POST)
    public @ResponseBody
    String diagnose(@RequestParam("file") MultipartFile file) throws IOException {
        UUID requestId = UUID.randomUUID();
        log.info("[/diagnose] [ID:{}]",requestId);
        if (!file.isEmpty ()) {
            byte[] r = virusScanner.scan ( file.getInputStream () );
            log.info("[/diagnose] [ID:{}] [success] ",requestId);
            return "It is a Good File : " + virusScanner.isCleanReply ( r ) + "\n";
        } else{
            log.info("[/diagnose][ID:{}][error]:empty file",requestId);
            throw new IllegalArgumentException ("empty file" );
        }
    }


    @RequestMapping(value = "/diagnosticReport", method = RequestMethod.POST)
    public @ResponseBody
    String diagnosticReport(@RequestParam("file") MultipartFile file) throws IOException {
        UUID requestId = UUID.randomUUID();
        log.info("[/diagnosticReport] [ID:{}]",requestId);
        if (!file.isEmpty ()) {
            log.info("[/diagnosticReport] [ID:{}] [success] ",requestId);
            return new String ( virusScanner.scan ( file.getInputStream () ) );
        } else {
            log.info("[/diagnosticReport][ID:{}][error]:empty file",requestId);
            throw new IllegalArgumentException ( "empty file" );
        }
    }
}
