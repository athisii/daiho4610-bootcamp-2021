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
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileDto updateProfileDto, Principal principal) {
        customerService.updateProfile(principal.getName(), updateProfileDto);
        return new ResponseEntity<>("Profile updated successfully!", HttpStatus.OK);
    }

    @GetMapping("/customer/address")
    public List<Address> addresses(Principal principal) {
        return customerService.addresses(principal.getName());
    }

    @PostMapping("/customer/add-address")
    public ResponseEntity<String> addAddress(@Valid @RequestBody AddressDto addressDto, Principal principal) {
        customerService.addAddress(principal.getName(), addressDto);
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
    public ResponseEntity<String> updatePassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto, Principal principal) {
        customerService.updatePassword(principal.getName(), resetPasswordDto);
        return new ResponseEntity<>("Password updated successfully!", HttpStatus.OK);
    }

    @GetMapping("/customer/profile")
    public MappingJacksonValue profile(Principal principal) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerService.viewProfile(principal.getName()));
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "firstName", "lastName", "imageUrl", "contact", "active");
        mappingJacksonValue.setFilters(filter(filter));
        return mappingJacksonValue;
    }

    private FilterProvider filter(SimpleBeanPropertyFilter filter) {
        return new SimpleFilterProvider().addFilter("userFilter", filter);
    }

    @GetMapping("/customer/category")
    public MappingJacksonValue retrieveParentCategories(@RequestBody(required = false) CategoryIdDto categoryIdDto) {
        if (categoryIdDto == null) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerService.getAllCategories(null));
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
            mappingJacksonValue.setFilters(new SimpleFilterProvider().addFilter("parentCategoryFilter", filter));
            return mappingJacksonValue;
        }
        Long categoryId = categoryIdDto.getCategoryId();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerService.getAllCategories(categoryId));
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "parentCategory");
        mappingJacksonValue.setFilters(new SimpleFilterProvider().addFilter("categoryFilter", filter).addFilter("parentCategoryFilter", filter));
        return mappingJacksonValue;
    }

    //Filtering
    @GetMapping("/customer/category/{categoryId}")
    public MappingJacksonValue retrieveFilterDetailsOfACategory(@PathVariable Long categoryId) {
        Category v = customerService.getFilterDetailsOfACategory(categoryId);
        System.out.println(v);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(v);
        SimpleBeanPropertyFilter filter1 = SimpleBeanPropertyFilter.filterOutAllExcept("id", "categoryMetadataFieldValues", "products");
        SimpleBeanPropertyFilter filter2 = SimpleBeanPropertyFilter.filterOutAllExcept("categoryMetadataField", "value");
        SimpleBeanPropertyFilter filter3 = SimpleBeanPropertyFilter.filterOutAllExcept("brand", "productVariations");
        SimpleBeanPropertyFilter filter4 = SimpleBeanPropertyFilter.filterOutAllExcept("price");
        SimpleFilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("categoryFilter", filter1)
                .addFilter("categoryMetadataFieldValuesFilter", filter2)
                .addFilter("productFilter", filter3)
                .addFilter("productVariationFilter", filter4);


        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping("/customer/product/{productId}")
    public MappingJacksonValue retrieveProduct(@PathVariable Long productId) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerService.getProduct(productId));
        return addFilter(mappingJacksonValue);
    }

    @GetMapping("/customer/category-product/{categoryId}")
    public MappingJacksonValue retrieveAllProducts(@PathVariable Long categoryId) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerService.getAllProducts(categoryId));
        return addFilter(mappingJacksonValue);
    }

    @GetMapping("/customer/similar-products/{productId}")
    public MappingJacksonValue retrieveSimilarProducts(@PathVariable Long productId) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerService.getSimilarProducts(productId));
        return addFilter(mappingJacksonValue);
    }

    private MappingJacksonValue addFilter(MappingJacksonValue mappingJacksonValue) {
        var filter1 = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "brand", "active", "returnable", "description", "cancelable", "category", "productVariations");
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
