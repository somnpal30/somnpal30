package com.sample.rabbitmq.config;

import com.github.javafaker.Faker;
import com.sample.rabbitmq.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;


@Configuration
public class ScheduledJob {
    @Value("${scheduler.enabled}")
    private boolean isSchedulerEnabled;
    private final RabbitTemplate rabbitTemplate;
    Logger log = LoggerFactory.getLogger(ScheduledJob.class);
    public AtomicInteger id;
    public Faker faker;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public ScheduledJob(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        faker = new Faker();
        id = new AtomicInteger(100);
    }

    @Scheduled(fixedRate = 5000)
    void sendMessage() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        if (isSchedulerEnabled) {
            rabbitTemplate.convertAndSend("direct-exchange", "sample-routing-key",
                    new Employee(faker.name().fullName(), "" + id.getAndIncrement()).toString()
            );
        }


    }
}
