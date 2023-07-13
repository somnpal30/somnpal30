package com.sample.rabbitmq.service;

import com.sample.rabbitmq.model.Employee;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSender {
   // private RabbitTemplate rabbitTemplate;

   /* @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
*/
  /*  @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingkey}")
    private String routingkey;
    @Value("${rabbitmq.queue}")
    private String queueName;*/

    public void send(Employee e) {
       // rabbitTemplate.convertAndSend(exchange, routingkey, e);
        //byte[] data = SerializationUtils.serialize(e);
        //rabbitTemplate.send(exchange,routingkey,new Message(data));
    }

    /*@Bean
    Queue queue() {
        return new Queue(queueName, false);
    }*/

}
