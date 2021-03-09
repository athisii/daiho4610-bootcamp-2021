package com.ttn.restfulwebservices.filtering;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilterUserService {
    private static final List<StaticFilterUser> staticFilterUsers = new ArrayList<>();
    private static final List<DynamicFilterUser> dynamicFilterUsers = new ArrayList<>();

    static {
        staticFilterUsers.add(new StaticFilterUser(1, "Suarabh", "password"));
        staticFilterUsers.add(new StaticFilterUser(2, "Athisii", "password"));
        staticFilterUsers.add(new StaticFilterUser(3, "Rose", "password"));

        dynamicFilterUsers.add(new DynamicFilterUser(1, "Mercy", "password"));
        dynamicFilterUsers.add(new DynamicFilterUser(2, "John", "password"));
        dynamicFilterUsers.add(new DynamicFilterUser(3, "Jack", "password"));
    }

    public List<StaticFilterUser> getAllStaticFilterUsers() {
        return staticFilterUsers;
    }

    public StaticFilterUser saveStaticFilterUser(StaticFilterUser user) {
        if (user.getId() == null) {
            user.setId(staticFilterUsers.size() + 1);
        }
        staticFilterUsers.add(user);
        return user;
    }

    public List<DynamicFilterUser> getAllDynamicFilterUsers() {
        return dynamicFilterUsers;
    }


    public DynamicFilterUser saveDynamicFilterUser(DynamicFilterUser user) {
        if (user.getId() == null) {
            user.setId(dynamicFilterUsers.size() + 1);
        }
        dynamicFilterUsers.add(user);
        return user;
    }

}
