package com.tothenew.services.user;

import com.tothenew.entities.product.Category;
import com.tothenew.entities.product.CategoryMetadataField;
import com.tothenew.entities.product.CategoryMetadataFieldValues;
import com.tothenew.entities.product.ParentCategory;
import com.tothenew.exception.CategoryExistException;
import com.tothenew.exception.CategoryMetadataFieldException;
import com.tothenew.objects.CategoryMetadataFieldDto;
import com.tothenew.objects.CreateCategoryDto;
import com.tothenew.objects.UpdateCategoryDto;
import com.tothenew.objects.categorymetadata.CategoryMetadataFieldValuesDto;
import com.tothenew.objects.categorymetadata.MetadataFieldId;
import com.tothenew.objects.categorymetadata.MetadataFieldValue;
import com.tothenew.repos.product.CategoryMetadataFieldRepository;
import com.tothenew.repos.product.CategoryMetadataFieldValuesRepository;
import com.tothenew.repos.product.CategoryRepository;
import com.tothenew.repos.product.ParentCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class AdminService {
    @Autowired
    private CategoryMetadataFieldRepository categoryMetadataFieldRepository;
    @Autowired
    private ParentCategoryRepository parentCategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;


    public Long addMetadataField(CategoryMetadataFieldDto cmfd) {
        if (checkIfMetadataFieldNameExists(cmfd.getName())) {
            throw new CategoryMetadataFieldException("Metadata Field Name Already Added.");
        }
        CategoryMetadataField cmf = new CategoryMetadataField(cmfd.getName());
        categoryMetadataFieldRepository.save(cmf);
        return cmf.getId();

    }

    public List<CategoryMetadataField> getAllCategoryMetadataFields() {
        return categoryMetadataFieldRepository.findAll();
    }

    public Long addCategory(CreateCategoryDto categoryDto) {
        if (checkIfCategoryNameExists(categoryDto.getName())) {
            throw new CategoryExistException("Same Category Name Already Exists");
        }
        if (categoryDto.getCategoryParentId() == null) {
            ParentCategory parentCategory = new ParentCategory(categoryDto.getName());
            parentCategoryRepository.save(parentCategory);
            return parentCategory.getId();
        } else {
            Optional<ParentCategory> parentCategoryOption = parentCategoryRepository.findById(categoryDto.getCategoryParentId());
            Category category = new Category(categoryDto.getName());

            parentCategoryOption.ifPresentOrElse(parentCategory -> {
                category.setParentCategory(parentCategory);
                parentCategory.getCategories().add(category);
                categoryRepository.save(category);
                parentCategoryRepository.save(parentCategory);
            }, () -> {
                throw new CategoryExistException("No ParentCategory found for id: " + categoryDto.getCategoryParentId());
            });
            return category.getId();
        }
    }

    private boolean checkIfMetadataFieldNameExists(String name) {
        return categoryMetadataFieldRepository.findByName(name) != null;
    }

    private boolean checkIfCategoryNameExists(String name) {
        return categoryRepository.findByName(name) != null || parentCategoryRepository.findByName(name) != null;
    }

    public Category getCategoryById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        categoryOptional.orElseThrow(() -> new CategoryExistException("Not found for category with id: " + categoryId));
        return categoryOptional.get();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void updateCategory(UpdateCategoryDto updateCategoryDto) {
        if (checkIfCategoryNameExists(updateCategoryDto.getName())) {
            throw new CategoryExistException("Same Category Name Already Exists");
        }
        Optional<Category> categoryOptional = categoryRepository.findById(updateCategoryDto.getId());
        categoryOptional.ifPresentOrElse(category -> {
            category.setName(updateCategoryDto.getName());
            categoryRepository.save(category);
        }, () -> {
            throw new CategoryExistException("Not found for category with id: " + updateCategoryDto.getId());
        });
    }

    public void addCategoryMetadataFieldValues(CategoryMetadataFieldValuesDto cmfvd) {
        Long categoryId = cmfvd.getCategoryId();

        if (checkIfCategoryIdExists(categoryId)) {
            List<MetadataFieldValue> values = new ArrayList<>(cmfvd.getValues());
            List<MetadataFieldId> mfIds = new ArrayList<>(cmfvd.getMetadataFieldIds());

            int mfIds_size = mfIds.size();

            if (values.size() != mfIds_size) {
                throw new CategoryMetadataFieldException("Number of metadataFieldIds and values should be equal and unique.");
            }
            mfIds.forEach(mfId -> {
                if (checkIfMetadataFieldIdExists(mfId.getId())) {
                    throw new CategoryMetadataFieldException("Not found Category Metadata Field with id: " + mfId.getId());
                }
            });
            mfIds.forEach(mfId -> {
                if (checkIfCategoryMetadataFieldKeyExists(cmfvd.getCategoryId(), mfId.getId())) {
                    throw new CategoryMetadataFieldException("Same CategoryMetadataFieldKey Already Exists");
                }
            });

            Category category = categoryRepository.findById(categoryId).get();
            List<CategoryMetadataFieldValues> cmfvs = new ArrayList<>();

            for (int i = 0; i < mfIds_size; i++) {
                CategoryMetadataFieldValues cmfv = new CategoryMetadataFieldValues();
                CategoryMetadataField cmf = categoryMetadataFieldRepository.findById(mfIds.get(i).getId()).get();
                cmfv.setCategory(category);
                cmfv.setCategoryMetadataField(cmf);
                cmfv.setValue(values.get(i).getValue());
                cmfvs.add(cmfv);
            }
            category.getCategoryMetadataFieldValues().addAll(cmfvs);
            categoryMetadataFieldValuesRepository.saveAll(cmfvs);
            categoryRepository.save(category);
            return;

        }
        throw new CategoryExistException("Not Found for category with id: " + categoryId);


    }

    private boolean checkIfMetadataFieldIdExists(Long metadataFieldId) {
        return categoryMetadataFieldRepository.findById(metadataFieldId).isEmpty();
    }

    private boolean checkIfCategoryIdExists(Long categoryId) {
        return categoryRepository.findById(categoryId).isPresent();
    }

    private boolean checkIfCategoryMetadataFieldKeyExists(Long categoryId, Long categoryMetadataFieldId) {
        return categoryMetadataFieldValuesRepository.findByKey(categoryId, categoryMetadataFieldId) != null;
    }


    public void updateCategoryMetadataFieldValues(CategoryMetadataFieldValuesDto cmfvd) {
        Long categoryId = cmfvd.getCategoryId();

        if (checkIfCategoryIdExists(categoryId)) {
            List<MetadataFieldValue> values = new ArrayList<>(cmfvd.getValues());
            List<MetadataFieldId> mfIds = new ArrayList<>(cmfvd.getMetadataFieldIds());

            int mfIds_size = mfIds.size();

            if (values.size() != mfIds_size) {
                throw new CategoryMetadataFieldException("Number of metadataFieldIds and values should be equal and unique.");
            }
            mfIds.forEach(mfId -> {
                if (checkIfMetadataFieldIdExists(mfId.getId())) {
                    throw new CategoryMetadataFieldException("Not found for Category Metadata Field with id: " + mfId.getId());
                }
            });
            mfIds.forEach(mfId -> {
                if (!checkIfCategoryMetadataFieldKeyExists(cmfvd.getCategoryId(), mfId.getId())) {
                    throw new CategoryMetadataFieldException("Not found for Category Id: " + categoryId + " associated with Metadata Field Id: " + mfId.getId());
                }
            });

            List<CategoryMetadataFieldValues> cmfvs = new ArrayList<>();

            for (int i = 0; i < mfIds_size; i++) {
                CategoryMetadataFieldValues cmfv = categoryMetadataFieldValuesRepository.findByKey(categoryId, mfIds.get(i).getId());
                cmfv.setValue(values.get(i).getValue());
                cmfvs.add(cmfv);
            }
            categoryMetadataFieldValuesRepository.saveAll(cmfvs);
            return;
        }
        throw new CategoryExistException("Not Found for category with id: " + categoryId);
    }

}
