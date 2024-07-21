package com.sample.springwebsecurity.controller;

import com.sample.springwebsecurity.entity.Country;
import com.sample.springwebsecurity.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "http://localhost:7080")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class WebController {

    private final CountryService countryService;


    @GetMapping("/country")
    public ResponseEntity<List<Country>> getCountries() {
        return ResponseEntity.ok(countryService.fetchCountries());
    }

    @GetMapping("/country/{countryId}")
    public ResponseEntity<Country> getCountries(@PathVariable String countryId) {
        return ResponseEntity.ok(countryService.fetchCountryById(countryId));
    }

    @DeleteMapping("/country/{countryId}")
    public ResponseEntity deleteCountry(@PathVariable String countryId) {
        countryService.deleteCoutryById(countryId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/country")
    public ResponseEntity<Country> createCountry(@RequestBody Country country) {
        return ResponseEntity.ok(countryService.createCountry(country));
    }

    @PostMapping(path = "/countries", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createCountries(@RequestPart(required = true, value = "file") MultipartFile multipartFile) throws IOException {
        // log.info("country : {}", country);
      /*  if(multipartFile != null && multipartFile.isEmpty()){
            log.info(new String(multipartFile.getBytes()));
        }*/

        return ResponseEntity.ok("OK");
        //return ResponseEntity.ok(countryService.createCountry(country));
    }

    @PostMapping(value = "/doc", name = "Upload Documents")
    public ResponseEntity<String> uploadDocumentV2(
            @RequestPart("file") MultipartFile file,
            @RequestParam String uploadedBy,
            @RequestParam String documentType,
            @RequestParam(required = false) String documentName

    ) {
        log.info("Received File :: {}", file.getOriginalFilename());
        try {
            System.out.println(new String(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("OK");
    }


    @PutMapping("/country/{countryId}")
    public ResponseEntity<String> deleteCountry(@PathVariable String countryId, @RequestBody Country country) {
        String status = "no of records update : %s".formatted(countryService.updateCoutryById(countryId, country));
        return ResponseEntity.ok(status);
    }

    @PutMapping("/country")
    public ResponseEntity<String> deleteCountry(@RequestBody Country country) {
        String status = "no of records update : %s".formatted(countryService.updateCoutry(country));
        return ResponseEntity.ok(status);
    }

}
