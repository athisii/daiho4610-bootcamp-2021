package com.tothenew.repos;

import com.tothenew.entities.user.Role;
import com.tothenew.entities.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByAuthority(String authority);
}
