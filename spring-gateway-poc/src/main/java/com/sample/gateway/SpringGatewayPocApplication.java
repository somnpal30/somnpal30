package com.sample.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;


@SpringBootApplication
public class SpringGatewayPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGatewayPocApplication.class, args);
    }


    @Bean
    public RouterFunction<ServerResponse> getRoute() {
        return route()
                .GET("/sample-web/**", http("http://localhost:7081"))
                .POST("/sample/**", http("http://localhost:8182"))
                .build();
    }
}


@RestController
@RequestMapping("/")
class Webcontroller {

    @GetMapping("/greet/{name}")
    public String greeting(@PathVariable String name) {
        return "Hello " + name;
    }

}