package com.sample.springrest.controller;

import com.github.javafaker.Faker;
import com.sample.springrest.model.Address;
import com.sample.springrest.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/")
public class SampleController {


    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getEmployee() {
        Faker faker = new Faker(new Locale("en_IND"));
        Employee e = Employee.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .addresses(List.of(
                        Address.builder()
                                .addressLine1(faker.address().streetAddress())
                                .city(faker.address().city())
                                .zip(faker.address().zipCode())
                                .country("IN")
                                .build()
                ))
                .build();


        return ResponseEntity.ok(List.of(e));
    }
}
