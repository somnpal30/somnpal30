package com.sample.natsproducer;

import com.sample.natsproducer.utils.NatsConnection;
import io.nats.client.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NatsService {
    private static Logger log = LoggerFactory.getLogger(NatsService.class);
    NatsConnection natsConnection;

    public NatsService(NatsConnection natsConnection) {
        this.natsConnection = natsConnection;
    }

    public CompletableFuture<Message> publish(NatsMessage natsMessage) {
       /* try {
            Message reply = this.natsConnection.getConnection().request("nats.queue.1", natsMessage.message().getBytes(), Duration.ofMillis(10));
            log.info(reply.getSubject());
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }*/
       // this.natsConnection.getConnection().publish("nats.queue.1", natsMessage.message().getBytes());

       CompletableFuture<Message> messageCompletableFuture
               = this.natsConnection.getConnection().request("nats.queue.1", natsMessage.message().getBytes());
        return messageCompletableFuture;
      /*  try {
            log.info("response from listener : {}" , new String(messageCompletableFuture.get().getData()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }*/


    }
}
