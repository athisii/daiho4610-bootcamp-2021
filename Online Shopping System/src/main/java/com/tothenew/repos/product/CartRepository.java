package com.tothenew.repos.product;

import com.tothenew.entities.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("from Cart where customer_id=?1 and product_variation_id=?2")
    Optional<Cart> findByKey(Long customerId, Long productVariationId);

    @Query("delete from Cart where customer_id=?1")
    @Modifying
    void deleteByCustomerId(Long id);
}
