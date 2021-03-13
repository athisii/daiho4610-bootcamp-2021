package com.tothenew.sprintbootjpa.entities;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Salary {
    private Double basicSalary;
    private Double bonusSalary;
    private Double taxAmount;
    private Double specialAllowanceSalary;

}
