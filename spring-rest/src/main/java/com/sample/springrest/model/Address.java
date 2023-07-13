package com.sample.springrest.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class Address {
    String addressLine1;
    String addressLine2;
    String city;
    String zip;
    String country;

}
