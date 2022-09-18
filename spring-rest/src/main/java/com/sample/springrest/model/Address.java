package com.sample.springrest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Address {
    String addressLine1;
    String addressLine2;
    String city;
    String zip;
    String country;

}
