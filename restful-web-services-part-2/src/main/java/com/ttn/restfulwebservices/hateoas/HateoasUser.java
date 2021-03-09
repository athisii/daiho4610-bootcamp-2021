package com.ttn.restfulwebservices.hateoas;

public class HateoasUser {
    private Integer id;
    private String username;

    public HateoasUser(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public HateoasUser setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public HateoasUser setUsername(String username) {
        this.username = username;
        return this;
    }
}
