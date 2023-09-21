package com.sample.userengine;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class UserEngineApplication {
	@Autowired RabbitTemplate rabbitTemplate;
	public static void main(String[] args) {
		SpringApplication.run(UserEngineApplication.class, args);
	}

	@RabbitListener( queues = {"user.engine.16"} )
	public void onMessage(Map<String,Object> response){
		System.out.println(response);
		rabbitTemplate.convertAndSend("user.engine.1",response);
	}


	public void onMessage(){

	}
}
