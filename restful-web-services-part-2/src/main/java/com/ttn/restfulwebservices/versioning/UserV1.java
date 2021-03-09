package com.ttn.restfulwebservices.versioning;

public class UserV1 {
    private Integer id;
    private String username;

    public UserV1(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public UserV1 setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserV1 setUsername(String username) {
        this.username = username;
        return this;
    }
}
