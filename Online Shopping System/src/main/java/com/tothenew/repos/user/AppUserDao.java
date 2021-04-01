package com.tothenew.repos.user;

import com.tothenew.entities.user.AppUserDetails;

public interface AppUserDao {
    AppUserDetails loadUserByUsername(String email);
}
