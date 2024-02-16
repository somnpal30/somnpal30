package com.sample.springwebsecurity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Country {

    @Id
    private String countryCode;
    private String countryName;


}
