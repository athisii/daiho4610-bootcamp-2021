package com.tothenew.entities.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    //parent category id;
//
//    @OneToOne(mappedBy = "category")
//    private CategoryMetadataFieldValues categoryMetadataFieldValues;


}
