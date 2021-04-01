package com.tothenew.repos.product;

import com.tothenew.entities.product.CategoryMetadataField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryMetadataFieldRepository extends JpaRepository<CategoryMetadataField, Long> {
    CategoryMetadataField findByName(String name);
}
