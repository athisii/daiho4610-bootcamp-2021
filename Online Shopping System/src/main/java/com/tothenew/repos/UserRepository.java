package com.tothenew.repos;

import com.tothenew.entities.user.Customer;
import com.tothenew.entities.user.Seller;
import com.tothenew.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("from Customer ")
    List<Customer> findAllCustomers();

    @Query("from Seller ")
    List<Seller> findAllSellers();

    User findByEmail(String email);

    @Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.email = ?2")
    @Modifying
    public void updateFailedAttempts(int failAttempts, String email);
}
