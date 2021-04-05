package com.tothenew.resources;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.tothenew.entities.product.Category;
import com.tothenew.entities.user.Address;
import com.tothenew.objects.*;
import com.tothenew.services.user.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@RestController
public class CustomerResource {
    @Autowired
    private CustomerService customerService;


    @PostMapping("/register/customer")
    public ResponseEntity<Object> registerCustomer(@Valid @RequestBody CustomerDto customerDto) {
        customerService.registerNewCustomer(customerDto);
        return new ResponseEntity<>("Your account has been created successfully, please check your email for activation.", HttpStatus.OK);
    }

    @PutMapping("/register/customer/confirm-account")
    public ResponseEntity<?> confirmRegistration(@RequestParam("token") String token) {
        customerService.confirmRegisteredCustomer(token);
        return new ResponseEntity<>("Your account has been verified successfully! You can now log in.", HttpStatus.OK);
    }

    @PostMapping("/register/customer/resend-token")
    public ResponseEntity<?> resendRegistrationToken(@Valid @RequestBody EmailDto emailDto) {
        customerService.resendToken(emailDto);
        return new ResponseEntity<>("Activation link has been resent.", HttpStatus.OK);
    }

    @PostMapping("/reset-password/customer")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody EmailDto emailDto) {
        customerService.resetPassword(emailDto);
        return new ResponseEntity<>("Link to reset the password has been sent.", HttpStatus.OK);
    }


    @PutMapping("/customer/profile")
    public ResponseEntity<?> updateProfile(@Valid Principal user, @RequestBody UpdateProfileDto updateProfileDto) {
        customerService.updateProfile(user.getName(), updateProfileDto);
        return new ResponseEntity<>("Profile updated successfully!", HttpStatus.OK);
    }

    @GetMapping("/customer/address")
    public List<Address> addresses(Principal user) {
        return customerService.addresses(user.getName());
    }

    @PostMapping("/customer/add-address")
    public ResponseEntity<String> addAddress(@Valid Principal user, @RequestBody AddressDto addressDto) {
        customerService.addAddress(user.getName(), addressDto);
        return new ResponseEntity<>("Address added successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/customer/delete-address")
    public ResponseEntity<String> removeAddress(@RequestParam Long addressId) {
        customerService.deleteAddress(addressId);
        return new ResponseEntity<>("Address deleted successfully!", HttpStatus.OK);
    }

    @PutMapping("/customer/update-address")
    public ResponseEntity<String> updateAddress(@Valid @RequestBody AddressDto addressDto, @RequestParam Long addressId) {
        customerService.updateAddress(addressDto, addressId);
        return new ResponseEntity<>("Address updated successfully!", HttpStatus.OK);
    }

    @PutMapping("/customer/update-password")
    public ResponseEntity<String> updatePassword(@Valid Principal user, @RequestBody ResetPasswordDto resetPasswordDto) {
        customerService.updatePassword(user.getName(), resetPasswordDto);
        return new ResponseEntity<>("Password updated successfully!", HttpStatus.OK);
    }

    @GetMapping("/customer/profile")
    public MappingJacksonValue profile(Principal user) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerService.viewProfile(user.getName()));
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "firstName", "lastName", "imageUrl", "contact", "active");
        mappingJacksonValue.setFilters(filter(filter));
        return mappingJacksonValue;
    }

    private FilterProvider filter(SimpleBeanPropertyFilter filter) {
        return new SimpleFilterProvider().addFilter("userFilter", filter);
    }

    @GetMapping("/customer/parent-category")
    public List<?> retrieveParentCategories() {
        return customerService.getAllCategories(null);

    }

    @GetMapping("/customer/parent-category/{parentId}")
    public MappingJacksonValue retrieveAllCategoriesWithSameParent(@PathVariable Long parentId) {
        List<?> categories = customerService.getAllCategories(parentId);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(categories);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "parentCategory");
        mappingJacksonValue.setFilters(new SimpleFilterProvider().addFilter("categoryFilter", filter));
        return mappingJacksonValue;
    }

    @GetMapping("/customer/category/{categoryId}")
    public MappingJacksonValue retrieveCategory(@PathVariable Long categoryId) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerService.getAllCategories(categoryId));
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "categoryMetadataFieldValues");
        mappingJacksonValue.setFilters(new SimpleFilterProvider().addFilter("categoryFilter", filter));
        return mappingJacksonValue;
    }

    @GetMapping("/customer/product/{productId}")
    public MappingJacksonValue retrieveProduct(@PathVariable Long productId) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerService.getProduct(productId));
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "brand", "active", "returnable", "description", "cancelable", "category");
        SimpleFilterProvider categoryFilter = new SimpleFilterProvider().addFilter("productFilter", filter).addFilter("categoryFilter", filter);
        mappingJacksonValue.setFilters(categoryFilter);
        return mappingJacksonValue;
    }

    @GetMapping("/customer/category-product/{categoryId}")
    public MappingJacksonValue retrieveAllProducts(@PathVariable Long categoryId) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerService.getAllProducts(categoryId));
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "brand", "active", "returnable", "description", "cancelable", "category");
        SimpleFilterProvider categoryFilter = new SimpleFilterProvider().addFilter("productFilter", filter).addFilter("categoryFilter", filter);
        mappingJacksonValue.setFilters(categoryFilter);
        return mappingJacksonValue;
    }


}
