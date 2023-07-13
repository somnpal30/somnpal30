package com.sample.springrest.controller;

import com.github.javafaker.Faker;
import com.sample.springrest.model.Address;
import com.sample.springrest.model.Employee;
import com.sample.springrest.model.LoginVO;
import com.sample.springrest.security.RSAUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class SampleController {

    private static Logger logger = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    RSAUtil rsaUtil;

    @Value("${download.file.path}")
    private String filename;

    @GetMapping("/cookie")
    public ResponseEntity getCookie(@CookieValue("refresh-token") String refreshtoken){
        logger.info("cookie : " + refreshtoken);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getEmployee(HttpServletRequest request, HttpServletResponse response ) {
        logger.info("is secure : " + request.isSecure());



        Cookie cookie = new Cookie("refresh-token", UUID.randomUUID().toString());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());
        response.addCookie(cookie);

        Faker faker = new Faker(new Locale("en_IND"));

        logger.info("x-forwarded-for : "+request.getHeader("x-forwarded-for"));


        List<Address> addressList = new ArrayList();
        addressList.add(Address.builder()
                .addressLine1(faker.address().streetAddress())
                .city(faker.address().city())
                .zip(faker.address().zipCode())
                .country("IN")
                .build());
        Employee e = Employee.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .emailId(faker.bothify("????##@gmail.com"))
                .addresses(addressList)
                .build();

        List<Employee> employees = new ArrayList();
        employees.add(e);
        logger.info(e.toString());

        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();


        return ResponseEntity.ok(employees);
    }

    @GetMapping("/downloadTemplate")
    public ResponseEntity<FileSystemResource> getTemplate() throws IOException {


        File f = getFile();
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_PDF_VALUE));
        respHeaders.setContentLength(f.length());
        respHeaders.setContentDispositionFormData("attachment", "test_" + LocalDateTime.now() + ".pdf");

        return new ResponseEntity<FileSystemResource>(
                new FileSystemResource(f), respHeaders, HttpStatus.OK
        );
    }

    private File getFile() {
        File file = new File(filename);
        return file;
    }

    @GetMapping(value = "/showTemplate")
    public void getPDF1(HttpServletResponse response) {
        String filename = "test_" + LocalDateTime.now() + ".pdf";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("content-disposition", "inline;filename=" + filename);

        try {
            Files.copy(getFile().toPath(), response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) {

        String content_type = file.getContentType();
        String extension_type = FilenameUtils.getExtension(file.getOriginalFilename());

        logger.info(file.getName());
        logger.info(file.getOriginalFilename());
        logger.info(content_type);

        logger.info(extension_type);
       // logger.info("" + StringUtils.contains(extension_type, content_type));
        logger.info("" + StringUtils.contains(content_type, extension_type));

        return ResponseEntity.ok().build();
    }


    @PostMapping("/login")
    public ResponseEntity<LoginVO> login(@RequestBody LoginVO loginVO, @RequestHeader Map<String, String> headers) {
        logger.info(loginVO.toString());
        //logger.info(headers.keySet().toString());
        try {
            logger.info(rsaUtil.decrypt(loginVO.getUsername()));
            logger.info(rsaUtil.decrypt(loginVO.getPassword()));
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(loginVO);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity doDelete(@PathVariable String id) {
        logger.info("Deleted : {}", id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/dummy1")
    public ResponseEntity<String> getClientKey(){
        return ResponseEntity.ok(rsaUtil.getPublicKey());
    }

    @GetMapping(value = "/ping" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Status> status(){
        return ResponseEntity.ok(new Status("UP"));
    }
}
class Status {
    String status ;
    public Status(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }


}