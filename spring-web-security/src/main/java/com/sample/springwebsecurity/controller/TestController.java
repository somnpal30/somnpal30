package com.sample.springwebsecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping(value = "/greet/{action:.+}")
    public ResponseEntity<String> greeting(
            @RequestHeader(value="Authorization", required=true) String authorization,
            @PathVariable String action
    ) {
        log.info(authorization);
        return ResponseEntity.ok().body("Hello World ! %s".formatted(action));
    }

}
//HTTPie command
//http -a admin:admin localhost:8081/sample-web/greet/somnath\n\npal