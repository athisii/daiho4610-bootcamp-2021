package com.tothenew.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
public class Address {
    private Integer streetNumber;
    private String location;
    private String state;
}
