package com.sample.natsproducer;

import com.sample.natsproducer.utils.NatsConnection;
import io.nats.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/nats")
public class NatsController {
    private static Logger logger = LoggerFactory.getLogger(NatsController.class);
    NatsService natsService;
    NatsConnection natsConnection;
    public NatsController(NatsService service, NatsConnection natsConnection) {
        this.natsService = service;
        this.natsConnection = natsConnection;
    }

    @PostMapping("/produce")
    public ResponseEntity<String> publishMessage(@RequestBody NatsMessage message) throws ExecutionException, InterruptedException {

        CompletableFuture<Message> messageCompletableFuture =  natsService.publish(message);
        //this.natsConnection.getConnection().subscribe("nats.queue.1",new)

        return ResponseEntity.ok(new String(messageCompletableFuture.get().getData()));
    }

    @Bean
    public CommandLineRunner commandLineRunner(){
        return  args -> {
            Connection connection = natsConnection.getConnection();

            Dispatcher dispatcher = connection.createDispatcher(message -> {
                logger.info("Receive message ({}) : ({})",new String(message.getData(), StandardCharsets.UTF_8), message.getSubject());
                //Thread.sleep(1000);
                connection.publish(message.getReplyTo(),"Welcome !".getBytes());
            });
            dispatcher.subscribe("nats.queue.1");


        };
    }

    /*@EventListener
    public void NatsListener1(){
        Connection connection = natsConnection.getConnection();

        Dispatcher dispatcher = connection.createDispatcher(message -> {
            logger.info("Receive message ({}) : ({})",new String(message.getData(), StandardCharsets.UTF_8), message.getSubject());
        });

        dispatcher.subscribe("nats.queue.1");
    }
*/
}
