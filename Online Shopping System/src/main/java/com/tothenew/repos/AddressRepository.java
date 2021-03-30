package com.tothenew.repos;

import com.tothenew.entities.user.Address;
import com.tothenew.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
}
