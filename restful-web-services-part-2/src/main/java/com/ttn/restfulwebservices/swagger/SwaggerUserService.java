package com.ttn.restfulwebservices.swagger;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SwaggerUserService {
    private static final List<SwaggerUser> users = new ArrayList<>();
    private int idCount = 3;

    static {
        users.add(new SwaggerUser(1, "Suarabh", "password"));
        users.add(new SwaggerUser(2, "Athisii", "password"));
        users.add(new SwaggerUser(3, "Rose", "password"));
    }

    public void saveUser(SwaggerUser user) {
        if (user.getId() == null) {
            user.setId(++idCount);
        }
        users.add(user);
    }


    public SwaggerUser getUserBy(int id) {
        for (SwaggerUser user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public void deleteUserById(int id) {
        users.removeIf(user -> user.getId() == id);
    }
}
