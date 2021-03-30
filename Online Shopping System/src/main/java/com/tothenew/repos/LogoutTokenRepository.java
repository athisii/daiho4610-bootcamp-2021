package com.tothenew.repos;

import com.tothenew.entities.token.LogoutToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogoutTokenRepository extends JpaRepository<LogoutToken, Long> {
    LogoutToken findByToken(String token);
}
