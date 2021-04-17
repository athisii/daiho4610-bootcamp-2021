package com.tothenew.repos.order;

import com.tothenew.entities.order.Order;
import com.tothenew.entities.user.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByCustomer(Customer customer, Pageable pageable);
}
