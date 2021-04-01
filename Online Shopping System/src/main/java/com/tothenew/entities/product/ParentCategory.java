package com.tothenew.entities.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ParentCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

//    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
//    private List<Category> categories = new ArrayList<>();

    public ParentCategory(String name) {
        this.name = name;
    }


}
