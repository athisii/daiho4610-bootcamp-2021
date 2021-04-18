package com.tothenew.repos.order;

import com.tothenew.entities.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
}
