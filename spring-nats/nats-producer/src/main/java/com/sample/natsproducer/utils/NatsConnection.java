package com.sample.natsproducer.utils;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NatsConnection {

    Connection connection;

    public NatsConnection() {
        Options options = Options.builder()
                .errorListener(NatsUtils.NATS_ERROR_LISTENER)
                .connectionListener(NatsUtils.NATS_CONNECTION_LISTENER)
                .build();


        try {
            this.connection = Nats.connect(options);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
