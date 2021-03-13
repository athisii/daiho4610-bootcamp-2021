package com.tothenew.sprintbootjpa.repos;

import com.tothenew.sprintbootjpa.entities.SingleTablePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleTablePaymentRepository extends JpaRepository<SingleTablePayment, Integer> {
}
