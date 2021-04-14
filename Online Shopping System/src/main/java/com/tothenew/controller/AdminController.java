package com.tothenew.controller;


import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.tothenew.entities.product.CategoryMetadataField;
import com.tothenew.objects.CategoryMetadataFieldDto;
import com.tothenew.objects.CreateCategoryDto;
import com.tothenew.objects.UpdateCategoryDto;
import com.tothenew.objects.categorymetadata.CategoryMetadataFieldValuesDto;
import com.tothenew.services.user.AdminService;
import com.tothenew.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/customer")
    public MappingJacksonValue profile(Pageable pageable) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(adminService.getAllCustomers(pageable));
        return addFilter(mappingJacksonValue);
    }

    @GetMapping("/seller")
    public MappingJacksonValue retrieveAllSellers(Pageable pageable) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(adminService.getAllSellers(pageable));
        return addFilter(mappingJacksonValue);
    }


    private MappingJacksonValue addFilter(MappingJacksonValue mappingJacksonValue) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAll();
        SimpleFilterProvider userFilter = new SimpleFilterProvider().addFilter("userFilter", filter);
        mappingJacksonValue.setFilters(userFilter);
        return mappingJacksonValue;

    }

    @PatchMapping("/activate-user")
    public ResponseEntity<?> activateUser(@RequestParam Long userId) {
        userService.activateUserById(userId);
        return new ResponseEntity<>("User account activated successfully!", HttpStatus.OK);
    }

    @PatchMapping("/deactivate-user")
    public ResponseEntity<?> deactivateUser(@RequestParam Long userId) {
        userService.deactivateUserById(userId);
        return new ResponseEntity<>("User account successfully deactivated!", HttpStatus.OK);
    }

    @GetMapping("/metadata-field")
    public Page<CategoryMetadataField> retrieveAllCategoryMetadataFields(Pageable pageable) {
        return adminService.getAllCategoryMetadataFields(pageable);
    }

    @PostMapping("/add-metadata-field")
    public ResponseEntity<String> createMetadataField(@Valid @RequestBody CategoryMetadataFieldDto categoryMetadataFieldDto) {
        Long id = adminService.addMetadataField(categoryMetadataFieldDto);
        return new ResponseEntity<>("New Metadata Field Added Successfully with id: " + id, HttpStatus.OK);
    }

    @PostMapping("/add-category")
    public ResponseEntity<String> createCategory(@Valid @RequestBody CreateCategoryDto categoryDto) {
        Long id = adminService.addCategory(categoryDto);
        return new ResponseEntity<>("New Category Added Successfully with id: " + id, HttpStatus.OK);

    }

    @GetMapping("/category/{categoryId}")
    public MappingJacksonValue retrieveCategory(@PathVariable Long categoryId) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(adminService.getCategoryById(categoryId));
        SimpleBeanPropertyFilter filter1 = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "parentCategory");
        SimpleBeanPropertyFilter filter2 = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
        SimpleFilterProvider filters = new SimpleFilterProvider();
        filters.addFilter("categoryFilter", filter1)
                .addFilter("parentCategoryFilter", filter2);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @PatchMapping("/update-category")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody UpdateCategoryDto updateCategoryDto) {
        adminService.updateCategory(updateCategoryDto);
        return new ResponseEntity<>("Updated Successfully!", HttpStatus.OK);
    }

    @PostMapping("/add-category-metadata-field-values")
    public ResponseEntity<String> createCategoryMetadataFieldValues(@Valid @RequestBody CategoryMetadataFieldValuesDto createCategoryMetadataFieldValues) {
        adminService.addCategoryMetadataFieldValues(createCategoryMetadataFieldValues);
        return new ResponseEntity<>("Added Successfully!", HttpStatus.OK);
    }

    @PatchMapping("/update-category-metadata-field-values")
    public ResponseEntity<String> updateCategoryMetadataFieldValues(@Valid @RequestBody CategoryMetadataFieldValuesDto createCategoryMetadataFieldValues) {
        adminService.updateCategoryMetadataFieldValues(createCategoryMetadataFieldValues);
        return new ResponseEntity<>("Updated Successfully!", HttpStatus.OK);
    }

    @PatchMapping("/activate-product/{productId}")
    public ResponseEntity<String> activateProduct(@PathVariable Long productId) {
        adminService.activateProduct(productId);
        return new ResponseEntity<>("Product activated Successfully!", HttpStatus.OK);
    }

    @PatchMapping("/deactivate-product/{productId}")
    public ResponseEntity<String> deactivateProduct(@PathVariable Long productId) {
        adminService.deactivateProduct(productId);
        return new ResponseEntity<>("Product deactivated Successfully!", HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public MappingJacksonValue retrieveProduct(@PathVariable Long productId) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(adminService.getProductById(productId));
        return addFilters(mappingJacksonValue);
    }

    @GetMapping("/product")
    public MappingJacksonValue retrieveAllProducts(@PageableDefault(sort = "id") Pageable pageable) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(adminService.getAllProducts(pageable));
        return addFilters(mappingJacksonValue);
    }

    private MappingJacksonValue addFilters(MappingJacksonValue mappingJacksonValue) {
        var filter1 = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "brand", "active", "description", "cancelable", "category", "productVariations");
        var filter2 = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
        var filter3 = SimpleBeanPropertyFilter.filterOutAllExcept("id", "quantityAvailable", "price", "primaryImageName", "active", "metadata");
        SimpleFilterProvider categoryFilter = new SimpleFilterProvider()
                .addFilter("productFilter", filter1)
                .addFilter("categoryFilter", filter2)
                .addFilter("productVariationFilter", filter3);
        mappingJacksonValue.setFilters(categoryFilter);
        return mappingJacksonValue;
    }


}
