package com.ttn.restfulwebservices.versioning;

import java.util.Objects;

public class UserV2 {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String gender;

    public UserV2(Integer id, String firstName, String lastName, Integer age, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public UserV2 setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserV2 setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserV2 setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserV2 setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public UserV2 setGender(String gender) {
        this.gender = gender;
        return this;
    }
}
