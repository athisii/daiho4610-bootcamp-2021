package com.tothenew.repos.product;

import com.tothenew.entities.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("from Product where name=?1 and seller_id=?2 and brand=?3 and category_id=?4")
    Product findByNameSellerIdBrandCategoryId(String productName, Long sellerId, String brand, Long categoryId);
}
