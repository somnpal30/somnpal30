package com.sample.springrest.controller;

import com.github.javafaker.Faker;
import com.sample.springrest.model.Address;
import com.sample.springrest.model.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/")
public class SampleController {


    @Value("${download.file.path}")
    private String filename;

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getEmployee() {
        Faker faker = new Faker(new Locale("en_IND"));
        Employee e = Employee.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .addresses(List.of(
                        Address.builder()
                                .addressLine1(faker.address().streetAddress())
                                .city(faker.address().city())
                                .zip(faker.address().zipCode())
                                .country("IN")
                                .build()
                ))
                .build();


        return ResponseEntity.ok(List.of(e));
    }

    @GetMapping("/downloadTemplate")
    public ResponseEntity<FileSystemResource> getTemplate() throws IOException {


        File f = getFile();
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_PDF_VALUE));
        respHeaders.setContentLength(f.length());
        respHeaders.setContentDispositionFormData("attachment", "test_"+ LocalDateTime.now()+ ".pdf");

        return new ResponseEntity<FileSystemResource>(
                new FileSystemResource(f), respHeaders, HttpStatus.OK
        );
    }

    private File getFile()
    {
        File file = new File(filename);
        return  file;
    }
    @GetMapping(value="/showTemplate")
    public void getPDF1(HttpServletResponse response) {
    String filename = "test_"+ LocalDateTime.now()+ ".pdf";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("content-disposition", "inline;filename=" + filename);

        try
        {
           Files.copy(getFile().toPath(), response.getOutputStream());
            response.getOutputStream().flush();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }


    }

}
