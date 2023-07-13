package com.sample.springrest.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class Employee {
    String firstname;
    String lastname;
    String emailId;
    List<Address> addresses;
}
