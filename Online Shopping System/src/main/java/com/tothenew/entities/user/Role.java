package com.tothenew.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(value = {"users"})
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private UserRole authority;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    List<User> users = new ArrayList<>();

    public Role(UserRole authority) {
        this.authority = authority;
    }
}
