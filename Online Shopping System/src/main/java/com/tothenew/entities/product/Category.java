package com.tothenew.entities.product;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonFilter("categoryFilter")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ParentCategory parentCategory;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.MERGE)
    private List<CategoryMetadataFieldValues> categoryMetadataFieldValues = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
