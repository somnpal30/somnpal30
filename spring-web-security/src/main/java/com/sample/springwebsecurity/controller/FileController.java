package com.sample.springwebsecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @PostMapping(value = "/upload")
    public ResponseEntity<String> handleFileUpload(@RequestPart("abc") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file");
        }

        try {
            String content = readTextFile(file);
            return ResponseEntity.ok("File content:\n" + content);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to read the file");
        }
    }

    private String readTextFile(MultipartFile file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    @PostMapping(value = "/upload-doc", name = "Upload Documents")
    public ResponseEntity<String> uploadDocumentV2(
            @RequestPart("file") MultipartFile file,
            HttpServletRequest request

    ) {
        return ResponseEntity.ok("OK");


    }
}
