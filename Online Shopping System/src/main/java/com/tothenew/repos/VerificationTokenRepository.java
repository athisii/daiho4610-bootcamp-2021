package com.tothenew.repos;

import com.tothenew.entities.token.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String verificationToken);

    @Query("from VerificationToken where user_id=:id")
    VerificationToken findTokenByUserId(@Param("id") Long id);
}
