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

    public FromStatus(String name) {
        this.name = name;
    }
}
