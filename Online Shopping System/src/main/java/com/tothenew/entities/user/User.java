package com.tothenew.entities.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.cloud.CloudFoundryVcapEnvironmentPostProcessor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;


//@JsonFilter("UserFilter")
@Entity
@Data
@EqualsAndHashCode(exclude = {"roles", "addresses"})
//@JsonIgnoreProperties(value = {"password", "addresses", "roles"})
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String imageUrl;
    private boolean isDeleted;
    private boolean isActive;
    private boolean accountNonLocked = true;
    private int failedAttempt;
    private Date lockTime;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

}
