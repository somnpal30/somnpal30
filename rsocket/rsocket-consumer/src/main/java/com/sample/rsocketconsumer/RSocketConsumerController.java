package com.sample.rsocketconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class RSocketConsumerController {


    @MessageMapping("fire-and-forget")
    public void greet(String name){
        log.info("consuming message : {}" , name);
    }

    @MessageMapping("request-response")
    public String requestResponse(String string){
        String response = "Hello " + string;
        log.info("Receive : {} and Returing : {}", string, response);
        return response;
    }
}
