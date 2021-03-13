package com.tothenew.sprintbootjpa.entities;

//Implement and demonstrate Embedded mapping using employee table having following fields:
// id, firstName, lastName, age, basicSalary, bonusSalary, taxAmount, specialAllowanceSalary.

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class EmbEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    @Embedded
    private Salary salary;

}
