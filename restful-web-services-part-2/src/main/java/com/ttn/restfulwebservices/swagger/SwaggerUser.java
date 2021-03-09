package com.ttn.restfulwebservices.swagger;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Details about the user.")
public class SwaggerUser {
    @ApiModelProperty(notes = "The unique id of the user")
    private Integer id;

    @ApiModelProperty(notes = "The user's name")
    private String username;

    @ApiModelProperty(notes = "The user's password")
    private String password;

    public SwaggerUser(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public SwaggerUser setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SwaggerUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SwaggerUser setPassword(String password) {
        this.password = password;
        return this;
    }
}
