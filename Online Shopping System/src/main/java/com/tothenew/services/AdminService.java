package com.tothenew.services;

import com.tothenew.entities.product.CategoryMetadataField;
import com.tothenew.exception.CategoryMetadataFieldException;
import com.tothenew.objects.CategoryMetadataFieldDto;
import com.tothenew.repos.product.CategoryMetadataFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private CategoryMetadataFieldRepository categoryMetadataFieldRepository;

    private boolean nameExists(String name) {
        return categoryMetadataFieldRepository.findByName(name) != null;
    }

    public Long addMetadataField(CategoryMetadataFieldDto categoryMetadataFieldDto) {
        if (nameExists(categoryMetadataFieldDto.getName())) {
            throw new CategoryMetadataFieldException("Metadata name already added.");
        }
        CategoryMetadataField cmf = new CategoryMetadataField(categoryMetadataFieldDto.getName());
        categoryMetadataFieldRepository.save(cmf);
        return cmf.getId();

    }

    public List<CategoryMetadataField> getAllCategoryMetadataFields() {
        return categoryMetadataFieldRepository.findAll();
    }
}
