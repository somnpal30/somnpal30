package com.sample.springrest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Employee {
    String firstname;
    String lastname;
    List<Address> addresses;
}
