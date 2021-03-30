package com.tothenew.repos;

import com.tothenew.entities.user.AppUserDetails;

public interface AppUserDao {
    AppUserDetails loadUserByUsername(String email);
}
