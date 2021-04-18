package com.tothenew.entities.order;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class ToStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "smallint")
    private int id;
    private String name;
    private int value;

    public ToStatus(String name, int value) {
        this.name = name;
        this.value = value;
    }
}
