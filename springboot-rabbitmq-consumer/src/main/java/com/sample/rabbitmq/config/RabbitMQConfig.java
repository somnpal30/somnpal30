package com.sample.rabbitmq.config;

import com.sample.rabbitmq.receiver.RabbitMQListner;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingkey}")
    private String routingkey;
    @Value("${rabbitmq.queue}")
    private String queueName;

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    Queue queue1() {
        return new Queue(queueName + 1, false);
    }

    @Bean
    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(queue(), queue1());
        simpleMessageListenerContainer.setMessageListener(rabbitMQListener());
        // simpleMessageListenerContainer.setConcurrency("100");

        return simpleMessageListenerContainer;

    }

    public RabbitMQListner rabbitMQListener() {
        RabbitMQListner rabbitMQListner = new RabbitMQListner();
        return rabbitMQListner;
    }

    /*
     * @Bean
     * public Jackson2JsonMessageConverter jsonMessageConverter() {
     * return new Jackson2JsonMessageConverter("com.sample");
     * }
     * 
     * @Bean
     * public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
     * final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
     * rabbitTemplate.setExchange(exchange);
     * rabbitTemplate.setMessageConverter(jsonMessageConverter());
     * return rabbitTemplate;
     * }
     */
}
