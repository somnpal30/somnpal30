package com.sample.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringbootRabbitmqProducerApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringbootRabbitmqProducerApplication.class, args);
    }

}
