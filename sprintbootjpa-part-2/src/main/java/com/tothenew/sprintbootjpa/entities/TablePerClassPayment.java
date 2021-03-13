package com.tothenew.sprintbootjpa.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class TablePerClassPayment {
    @Id
    private Integer id;
    private Double amount;

}
