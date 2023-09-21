package com.sample.rsocketproducer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class RSocketProducerController {
    AtomicInteger counter;



    RSocketClient rSocketClient;

    public RSocketProducerController(RSocketClient rSocketClient) {
        this.rSocketClient = rSocketClient;
        counter = new AtomicInteger(0);
    }

    @GetMapping("/message/{name}")
    public ResponseEntity fireForget(@PathVariable String name) {
        this.rSocketClient.getRSocketRequester().
                route("fire-and-forget")
                .data(name +":" + counter.getAndIncrement())
                .send()
                .block();
        return ResponseEntity.ok().build();
    }
}
