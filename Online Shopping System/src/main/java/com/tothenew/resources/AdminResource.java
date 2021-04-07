package com.tothenew.resources;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.tothenew.entities.product.CategoryMetadataField;
import com.tothenew.entities.product.Product;
import com.tothenew.objects.CreateCategoryDto;
import com.tothenew.objects.CategoryMetadataFieldDto;
import com.tothenew.objects.categorymetadata.CategoryMetadataFieldValuesDto;
import com.tothenew.objects.UpdateCategoryDto;
import com.tothenew.services.user.AdminService;
import com.tothenew.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class AdminResource {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/customer")
    public MappingJacksonValue profile() {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userService.getAllCustomers());
        mappingJacksonValue.setFilters(filter());
        return mappingJacksonValue;
    }

    @GetMapping("/admin/seller")
    public MappingJacksonValue retrieveAllSellers() {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userService.getAllSellers());
        mappingJacksonValue.setFilters(filter());
        return mappingJacksonValue;
    }

    private FilterProvider filter() {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAll();
        return new SimpleFilterProvider().addFilter("userFilter", filter);
    }

    @PutMapping("/admin/activate-user")
    public ResponseEntity<?> activateUser(@RequestParam Long userId) {
        userService.activateUserById(userId);
        return new ResponseEntity<>("User account activated successfully!", HttpStatus.OK);
    }

    @PutMapping("/admin/deactivate-user")
    public ResponseEntity<?> deactivateUser(@RequestParam Long userId) {
        userService.deactivateUserById(userId);
        return new ResponseEntity<>("User account successfully deactivated!", HttpStatus.OK);
    }

    @GetMapping("/admin/metadata-field")
    public List<CategoryMetadataField> retrieveAllCategoryMetadataFields() {
        return adminService.getAllCategoryMetadataFields();
    }

    @PostMapping("/admin/add-metadata-field")
    public ResponseEntity<String> createMetadataField(@Valid @RequestBody CategoryMetadataFieldDto categoryMetadataFieldDto) {
        Long id = adminService.addMetadataField(categoryMetadataFieldDto);
        return new ResponseEntity<>("New Metadata Field Added Successfully with id: " + id, HttpStatus.OK);
    }

    @PostMapping("/admin/add-category")
    public ResponseEntity<String> createCategory(@Valid @RequestBody CreateCategoryDto categoryDto) {
        Long id = adminService.addCategory(categoryDto);
        return new ResponseEntity<>("New Category Added Successfully with id: " + id, HttpStatus.OK);

    }

    @GetMapping("/admin/category/{categoryId}")
    public MappingJacksonValue retrieveCategory(@PathVariable Long categoryId) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(adminService.getCategoryById(categoryId));
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "parentCategory", "categoryMetadataFieldValues");
        mappingJacksonValue.setFilters(new SimpleFilterProvider().addFilter("categoryFilter", filter));
        return mappingJacksonValue;
    }

    @PutMapping("/admin/update-category")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody UpdateCategoryDto updateCategoryDto) {
        adminService.updateCategory(updateCategoryDto);
        return new ResponseEntity<>("Updated Successfully!", HttpStatus.OK);
    }

    @PostMapping("/admin/add-category-metadata-field-values")
    public ResponseEntity<String> createCategoryMetadataFieldValues(@Valid @RequestBody CategoryMetadataFieldValuesDto createCategoryMetadataFieldValues) {
        adminService.addCategoryMetadataFieldValues(createCategoryMetadataFieldValues);
        return new ResponseEntity<>("Added Successfully!", HttpStatus.OK);
    }

    @PutMapping("/admin/update-category-metadata-field-values")
    public ResponseEntity<String> updateCategoryMetadataFieldValues(@Valid @RequestBody CategoryMetadataFieldValuesDto createCategoryMetadataFieldValues) {
        adminService.updateCategoryMetadataFieldValues(createCategoryMetadataFieldValues);
        return new ResponseEntity<>("Updated Successfully!", HttpStatus.OK);
    }

    @PutMapping("/admin/activate-product/{productId}")
    public ResponseEntity<String> activateProduct(@PathVariable Long productId) {
        adminService.activateProduct(productId);
        return new ResponseEntity<>("Product activated Successfully!", HttpStatus.OK);
    }

    @PutMapping("/admin/deactivate-product/{productId}")
    public ResponseEntity<String> deactivateProduct(@PathVariable Long productId) {
        adminService.deactivateProduct(productId);
        return new ResponseEntity<>("Product deactivated Successfully!", HttpStatus.OK);
    }

    @GetMapping("/admin/product/{productId}")
    public MappingJacksonValue retrieveProduct(@PathVariable Long productId) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(adminService.getProductById(productId));
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "brand", "name", "active", "returnable", "description", "productVariations", "cancelable", "category", "seller");
        SimpleFilterProvider categoryFilter = new SimpleFilterProvider().addFilter("productFilter", filter).addFilter("categoryFilter", filter).addFilter("userFilter", filter);
        mappingJacksonValue.setFilters(categoryFilter);
        return mappingJacksonValue;
    }

    @GetMapping("/admin/product")
    public MappingJacksonValue retrieveAllProducts() {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(adminService.getAllProducts());
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "brand", "name", "active", "returnable", "description", "productVariations", "cancelable", "category", "seller");
        SimpleFilterProvider categoryFilter = new SimpleFilterProvider().addFilter("productFilter", filter).addFilter("categoryFilter", filter).addFilter("userFilter", filter);
        mappingJacksonValue.setFilters(categoryFilter);
        return mappingJacksonValue;
    }


}
