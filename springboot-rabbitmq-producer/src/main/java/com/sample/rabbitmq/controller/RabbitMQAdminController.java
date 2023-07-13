package com.sample.rabbitmq.controller;

import com.rabbitmq.client.Channel;
import com.sample.rabbitmq.config.RabbitAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class RabbitMQAdminController {

    @Autowired
    RabbitAdminService rabbitAdminService;



    @GetMapping("/exchange")
    public ResponseEntity<String> exchangeName() {

        return ResponseEntity.ok(rabbitAdminService.routingKey());
    }
}
