package com.sample.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitAdminService {
    private static Logger logger = LoggerFactory.getLogger(RabbitAdminService.class);
    @Autowired
    RabbitTemplate rabbitTemplate;

    RabbitAdmin admin;

    public String routingKey() {
        logger.info(rabbitTemplate.getRoutingKey());

        return rabbitTemplate.getExchange();
    }
}
