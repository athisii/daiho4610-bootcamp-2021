package com.tothenew.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(value = {"users"})
@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String authority;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    List<User> users = new ArrayList<>();

}
