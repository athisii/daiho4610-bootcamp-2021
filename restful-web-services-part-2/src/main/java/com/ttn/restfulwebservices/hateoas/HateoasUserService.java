package com.ttn.restfulwebservices.hateoas;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HateoasUserService {
    private static final List<HateoasUser> users = new ArrayList<>();

    static {
        users.add(new HateoasUser(1, "Saurabh"));
        users.add(new HateoasUser(2, "Athisii"));
        users.add(new HateoasUser(3, "Rose"));
    }

    public List<HateoasUser> getAllUsers() {
        return users;
    }

    public HateoasUser findById(int id) {
        for (var user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}
