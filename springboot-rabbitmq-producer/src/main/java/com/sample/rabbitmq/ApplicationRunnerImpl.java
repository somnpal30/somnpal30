package com.sample.rabbitmq;

import com.github.javafaker.Faker;
import com.sample.rabbitmq.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    Logger log = LoggerFactory.getLogger(ApplicationRunnerImpl.class);
    AtomicInteger id ;
/*    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingkey}")
    private String routingkey;
    @Value("${rabbitmq.queue}")
    private String queueName;*/

    @Value("${scheduler.enabled}")
    private boolean isSchedulerEnabled;

    private final RabbitTemplate rabbitTemplate;
    Faker faker;
    public ApplicationRunnerImpl(RabbitTemplate rabbitTemplate) {
       this.rabbitTemplate = rabbitTemplate;
        faker = new Faker();
        id = new AtomicInteger(100);
    }




    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Running.......................");

    }

    //@Scheduled(fixedRate = 5000)
    void sendMessage() {

        if(isSchedulerEnabled){
            log.info("The time is now {}", dateFormat.format(new Date()));
            rabbitTemplate.convertAndSend("direct-exchange", "sample-routing-key",
                    new Employee(faker.name().fullName(),  ""+id.getAndIncrement() ).toString()
            );
        }else {
            log.info("Scheduler Disabled.");
        }

    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //@Scheduled(fixedRate = 1000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
