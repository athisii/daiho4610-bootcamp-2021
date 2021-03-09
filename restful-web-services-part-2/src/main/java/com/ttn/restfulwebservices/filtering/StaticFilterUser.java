package com.ttn.restfulwebservices.filtering;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"password"})
public class StaticFilterUser{
    private Integer id;
    private String name;
    private String password;

    public StaticFilterUser(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public StaticFilterUser setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public StaticFilterUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public StaticFilterUser setPassword(String password) {
        this.password = password;
        return this;
    }
}
