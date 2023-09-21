package com.sample.rsocketproducer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
@Slf4j
@Getter
public class RSocketClient {


    private RSocketRequester rSocketRequester;

    public RSocketClient(RSocketRequester.Builder builder) {
        this.rSocketRequester = builder.rsocketConnector(connector ->
                        Retry.fixedDelay(3, Duration.ofMillis(500)))
                .rsocketStrategies(configurer -> {
                    configurer.encoder(new Jackson2CborEncoder())
                            .decoder(new Jackson2CborDecoder());
                }).dataMimeType(MediaType.APPLICATION_CBOR)
                .tcp("localhost", 6080);

    }
}
