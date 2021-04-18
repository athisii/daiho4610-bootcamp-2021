package com.tothenew.entities.order;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class FromStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "smallint")
    private int id;
    private String name;
    private int value;

    public FromStatus(String name, int value) {
        this.name = name;
        this.value = value;
    }
}
