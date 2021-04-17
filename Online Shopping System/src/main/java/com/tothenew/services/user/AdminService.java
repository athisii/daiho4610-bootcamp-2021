package com.tothenew.services.user;

import com.tothenew.entities.order.Order;
import com.tothenew.entities.product.*;
import com.tothenew.entities.user.Customer;
import com.tothenew.entities.user.Seller;
import com.tothenew.exception.*;
import com.tothenew.objects.CategoryMetadataFieldDto;
import com.tothenew.objects.CreateCategoryDto;
import com.tothenew.objects.UpdateCategoryDto;
import com.tothenew.objects.categorymetadata.CategoryMetadataFieldValuesDto;
import com.tothenew.objects.categorymetadata.MetadataFieldIdValue;
import com.tothenew.repos.order.OrderRepository;
import com.tothenew.repos.product.*;
import com.tothenew.repos.user.CustomerRepository;
import com.tothenew.repos.user.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;


    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Page<Seller> getAllSellers(Pageable pageable) {
        return sellerRepository.findAll(pageable);
    }


    public Long addMetadataField(CategoryMetadataFieldDto cmfd) {
        if (checkIfMetadataFieldNameExists(cmfd.getName())) {
            throw new CategoryMetadataFieldExistException("Metadata Field Name Already Added.");
        }
        CategoryMetadataField cmf = new CategoryMetadataField(cmfd.getName());
        categoryMetadataFieldRepository.save(cmf);
        return cmf.getId();

    }

    public Page<CategoryMetadataField> getAllCategoryMetadataFields(Pageable pageable) {
        return categoryMetadataFieldRepository.findAll(pageable);
    }

    public Long addCategory(CreateCategoryDto categoryDto) {
        if (checkIfCategoryNameExists(categoryDto.getName())) {
            throw new CategoryExistException("Same Category Name Already Exists");
        }
        if (categoryDto.getCategoryParentId() == null) {
            ParentCategory parentCategory = new ParentCategory(categoryDto.getName());
            parentCategoryRepository.save(parentCategory);
            return parentCategory.getId();
        }
        //Else
        Optional<ParentCategory> parentCategoryOption = parentCategoryRepository.findById(categoryDto.getCategoryParentId());
        Category category = new Category(categoryDto.getName());

        parentCategoryOption.ifPresentOrElse(parentCategory -> {
            category.setParentCategory(parentCategory);
            parentCategory.getCategories().add(category);
            categoryRepository.save(category);
            parentCategoryRepository.save(parentCategory);
        }, () -> {
            throw new GenericNotFoundException("No ParentCategory found for id: " + categoryDto.getCategoryParentId());
        });
        return category.getId();

    }

    private boolean checkIfMetadataFieldNameExists(String name) {
        return categoryMetadataFieldRepository.findByName(name) != null;
    }

    private boolean checkIfCategoryNameExists(String name) {
        return categoryRepository.findByName(name) != null || parentCategoryRepository.findByName(name) != null;
    }

    public List<Category> getCategoryById(Long categoryId) {
        Optional<ParentCategory> categoryOptional = parentCategoryRepository.findById(categoryId);
        categoryOptional.orElseThrow(() -> new GenericNotFoundException("Not found for category with id: " + categoryId));
        return categoryOptional.get().getCategories();
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
            throw new GenericNotFoundException("Not found for category with id: " + updateCategoryDto.getId());
        });
    }

    public void addCategoryMetadataFieldValues(CategoryMetadataFieldValuesDto cmfvd) {
        Long categoryId = cmfvd.getCategoryId();

        if (checkIfCategoryIdExists(categoryId)) {
            LinkedHashSet<MetadataFieldIdValue> mfIdvs = cmfvd.getMetadataFieldIdValues();
            mfIdvs.forEach(mfIdv -> {
                if (checkIfMetadataFieldIdIsEmpty(mfIdv.getMetadataFieldId())) {
                    throw new GenericNotFoundException("Not found Category Metadata Field with id: " + mfIdv.getMetadataFieldId());
                }
                if (checkIfCategoryMetadataFieldKeyExists(cmfvd.getCategoryId(), mfIdv.getMetadataFieldId())) {
                    throw new CategoryMetadataFieldExistException("Same CategoryMetadataFieldKey Already Exists");
                }
            });
            Category category = categoryRepository.findById(categoryId).get();
            List<CategoryMetadataFieldValues> cmfvs = new ArrayList<>();

            mfIdvs.forEach(mfIdv -> {
                CategoryMetadataFieldValues cmfv = new CategoryMetadataFieldValues();
                CategoryMetadataField cmf = categoryMetadataFieldRepository.findById(mfIdv.getMetadataFieldId()).get();
                cmfv.setCategory(category);
                cmfv.setCategoryMetadataField(cmf);
                cmfv.setValue(mfIdv.getValue());
                cmfvs.add(cmfv);
            });
            category.getCategoryMetadataFieldValues().addAll(cmfvs);
            categoryMetadataFieldValuesRepository.saveAll(cmfvs);
            return;

        }
        throw new GenericNotFoundException("Not Found for category with id: " + categoryId);


    }

    private boolean checkIfMetadataFieldIdIsEmpty(Long metadataFieldId) {
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
        LinkedHashSet<MetadataFieldIdValue> mfIdVs = cmfvd.getMetadataFieldIdValues();
        mfIdVs.forEach(mfIdv -> {
            if (!checkIfCategoryMetadataFieldKeyExists(cmfvd.getCategoryId(), mfIdv.getMetadataFieldId())) {
                throw new GenericNotFoundException("Not found for Category Id: " + categoryId + " associated with MetadataField Id: " + mfIdv.getMetadataFieldId());
            }
        });
        List<CategoryMetadataFieldValues> cmfvs = new ArrayList<>();
        mfIdVs.forEach(mfIdV -> {
            CategoryMetadataFieldValues cmfv = categoryMetadataFieldValuesRepository.findByKey(categoryId, mfIdV.getMetadataFieldId());
            cmfv.setValue(mfIdV.getValue());
            cmfvs.add(cmfv);
        });
        categoryMetadataFieldValuesRepository.saveAll(cmfvs);

    }

    public void activateProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.orElseThrow(() -> new GenericNotFoundException("Not found for product with id: " + productId));
        productOptional.ifPresent(product -> {
            if (product.isActive()) {
                throw new ProductActivationException("Product already activated");
            }
            product.setActive(true);
            productRepository.save(product);
            userService.sendProductActivatedMessage(product.getName(), product.getSeller().getEmail());
        });

    }

    public void deactivateProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.orElseThrow(() -> new GenericNotFoundException("Not found for product with id: " + productId));
        productOptional.ifPresent(product -> {
            if (!product.isActive()) {
                throw new ProductActivationException("Product already deactivated");
            }
            product.setActive(false);
            productRepository.save(product);
            userService.sendProductDeactivatedMessage(product.getName(), product.getSeller().getEmail());
        });
    }

    public Product getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.orElseThrow(() -> new GenericNotFoundException("Not found for product with id: " + productId));
        return productOptional.get();
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
}
