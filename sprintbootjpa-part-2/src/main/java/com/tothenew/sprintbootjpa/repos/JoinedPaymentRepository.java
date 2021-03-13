package com.tothenew.sprintbootjpa.repos;

import com.tothenew.sprintbootjpa.entities.JoinedPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinedPaymentRepository extends JpaRepository<JoinedPayment, Integer> {
}
