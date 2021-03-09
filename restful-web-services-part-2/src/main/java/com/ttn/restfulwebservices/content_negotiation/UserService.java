package com.ttn.restfulwebservices.content_negotiation;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static final List<User> users = new ArrayList<>();
    private int idCount = 3;

    static {
        users.add(new User(1, "Suarabh", "password"));
        users.add(new User(2, "Athisii", "password"));
        users.add(new User(3, "Rose", "password"));
    }

    public void saveUser(User user) {
        if (user.getId() == null) {
            user.setId(++idCount);
        }
        users.add(user);
    }

    public List<User> getAllUsers() {
        return users;
    }


}
