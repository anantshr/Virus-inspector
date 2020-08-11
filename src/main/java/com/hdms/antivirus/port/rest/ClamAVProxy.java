package com.hdms.antivirus.port.rest;

import com.hdms.antivirus.domain.Scanner;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ClamAVProxy {

    private final Scanner scanner;

    /**
     * @return Clamd scan result
     */
    @RequestMapping(value = "/scan", method = RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(@RequestParam("name") String name,
                            @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] r = scanner.scan(file.getInputStream());
            return "Everything ok : " + scanner.isCleanReply(r) + "\n";
        } else throw new IllegalArgumentException("empty file");
    }

    /**
     * @return Clamd scan reply
     */
    @RequestMapping(value = "/scanReply", method = RequestMethod.POST)
    public @ResponseBody
    String handleFileUploadReply(@RequestParam("name") String name,
                                 @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            return new String(scanner.scan(file.getInputStream()));
        } else throw new IllegalArgumentException("empty file");
    }
}
