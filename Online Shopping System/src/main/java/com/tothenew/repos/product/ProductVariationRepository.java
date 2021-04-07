package com.tothenew.repos.product;

import com.tothenew.entities.product.ProductVariation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {
    Page<ProductVariation> findByProductId(Long productId, Pageable pageable);
}
