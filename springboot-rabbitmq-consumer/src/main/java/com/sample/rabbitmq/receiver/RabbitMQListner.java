package com.sample.rabbitmq.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.rabbitmq.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMQListner implements MessageListener {
    Logger log = LoggerFactory.getLogger(RabbitMQListner.class);

    @Override
    public void onMessage(Message message) {
       // message.addAllowedListPatterns("com.sample");
       /* try {
            Employee e = getMessageBody(message.getBody());
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
        log.info("Consuming Message - " + new String(message.getBody()));
    }

    private Employee getMessageBody(byte[] message) throws IOException {

        Employee e = new ObjectMapper().readValue(message, Employee.class);

        return e;
    }
}