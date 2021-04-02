package com.tothenew.repos.product;

import com.tothenew.entities.product.ParentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentCategoryRepository extends JpaRepository<ParentCategory, Long> {
    ParentCategory findByName(String name);
}
