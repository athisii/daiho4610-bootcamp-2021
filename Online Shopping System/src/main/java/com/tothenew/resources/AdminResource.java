package com.tothenew.resources;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.tothenew.entities.product.Category;
import com.tothenew.entities.product.CategoryMetadataField;
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


@RequestMapping("/admin")
@RestController
public class AdminResource {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/customer")
    public MappingJacksonValue profile() {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userService.getAllCustomers());
        mappingJacksonValue.setFilters(filter());
        return mappingJacksonValue;
    }

    @GetMapping("/seller")
    public MappingJacksonValue retrieveAllSellers() {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userService.getAllSellers());
        mappingJacksonValue.setFilters(filter());
        return mappingJacksonValue;
    }

    private FilterProvider filter() {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAll();
        return new SimpleFilterProvider().addFilter("userFilter", filter);
    }

    @PutMapping("/activate-user")
    public ResponseEntity<?> activateUser(@RequestParam Long userId) {
        userService.activateUserById(userId);
        return new ResponseEntity<>("User account activated successfully!", HttpStatus.OK);
    }

    @PutMapping("/deactivate-user")
    public ResponseEntity<?> deactivateUser(@RequestParam Long userId) {
        userService.deactivateUserById(userId);
        return new ResponseEntity<>("User account successfully deactivated!", HttpStatus.OK);
    }

    @GetMapping("/metadata-field")
    public List<CategoryMetadataField> retrieveAllCategoryMetadataFields() {
        return adminService.getAllCategoryMetadataFields();
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
    public Category viewCategory(@PathVariable Long categoryId) {
        return adminService.getCategoryById(categoryId);
    }

    @GetMapping("/category")
    public List<Category> viewAllCategories() {
        return adminService.getAllCategories();
    }

    @PutMapping("/update-category")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody UpdateCategoryDto updateCategoryDto) {
        adminService.updateCategory(updateCategoryDto);
        return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
    }

    @PostMapping("/add-category-metadata-field-values")
    public ResponseEntity<String> createCategoryMetadataFieldValues(@Valid @RequestBody CategoryMetadataFieldValuesDto createCategoryMetadataFieldValues) {
        adminService.addCategoryMetadataFieldValues(createCategoryMetadataFieldValues);
        return new ResponseEntity<>("Added Successfully", HttpStatus.OK);
    }

    @PutMapping("update-category-metadata-field-values")
    public ResponseEntity<String> updateCategoryMetadataFieldValues(@Valid @RequestBody CategoryMetadataFieldValuesDto createCategoryMetadataFieldValues) {
        adminService.updateCategoryMetadataFieldValues(createCategoryMetadataFieldValues);
        return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
    }


}
