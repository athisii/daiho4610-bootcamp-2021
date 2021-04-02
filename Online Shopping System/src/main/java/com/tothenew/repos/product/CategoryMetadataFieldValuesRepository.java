package com.tothenew.repos.product;

import com.tothenew.entities.product.CategoryMetadataFieldValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryMetadataFieldValuesRepository extends JpaRepository<CategoryMetadataFieldValues, Long> {

    @Query("from CategoryMetadataFieldValues where category_id=?1 and category_metadata_field_id=?2")
    CategoryMetadataFieldValues findByKey(Long categoryId, Long categoryMetadataFieldId);
}
