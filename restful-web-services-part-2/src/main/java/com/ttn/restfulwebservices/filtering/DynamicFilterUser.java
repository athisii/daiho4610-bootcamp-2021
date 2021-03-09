package com.ttn.restfulwebservices.filtering;


import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("DynamicFilterUser")
public class DynamicFilterUser {
    private Integer id;
    private String name;
    private String password;

    public DynamicFilterUser(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public DynamicFilterUser setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DynamicFilterUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public DynamicFilterUser setPassword(String password) {
        this.password = password;
        return this;
    }
}
