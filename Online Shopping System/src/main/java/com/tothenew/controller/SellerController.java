package com.tothenew.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.tothenew.objects.*;
import com.tothenew.objects.product.CreateProductDto;
import com.tothenew.objects.product.CreateProductVariationDto;
import com.tothenew.objects.product.UpdateProductDto;
import com.tothenew.objects.product.UpdateProductVariationDto;
import com.tothenew.services.user.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@RestController
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @PostMapping("/register/seller")
    public ResponseEntity<?> registerSeller(@Valid @RequestBody SellerDto sellerDto) {
        sellerService.registerNewSeller(sellerDto);
        return new ResponseEntity<>("Your account has been created successfully, waiting for approval!", HttpStatus.OK);
    }

    @PostMapping("/reset-password/seller")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody EmailDto emailDto) {
        sellerService.resendToken(emailDto);
        return new ResponseEntity<>("Link to reset the password has been sent.", HttpStatus.OK);
    }

    @GetMapping("/seller/profile")
    public MappingJacksonValue profile(Principal principal) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(sellerService.viewProfile(principal.getName()));
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "firstName", "lastName", "imageUrl", "companyContact", "companyName", "active", "addresses");
        mappingJacksonValue.setFilters(filter(filter));
        return mappingJacksonValue;
    }

    private FilterProvider filter(SimpleBeanPropertyFilter filter) {
        return new SimpleFilterProvider().addFilter("userFilter", filter);
    }

    @PatchMapping("/seller/profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileDto updateProfileDto, Principal principal) {
        sellerService.updateProfile(principal.getName(), updateProfileDto);
        return new ResponseEntity<>("Profile updated successfully!", HttpStatus.OK);
    }

    @PatchMapping("/seller/update-password")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto, Principal principal) {
        sellerService.updatePassword(principal.getName(), resetPasswordDto);
        return new ResponseEntity<>("Password updated successfully!", HttpStatus.OK);
    }

    @PatchMapping("/seller/update-address")
    public ResponseEntity<String> updateAddress(@Valid @RequestBody AddressDto addressDto, @RequestParam Long addressId) {
        sellerService.updateAddress(addressDto, addressId);
        return new ResponseEntity<>("Address updated successfully!", HttpStatus.OK);
    }


    @PostMapping("/seller/add-product")
    public ResponseEntity<String> createProduct(@Valid @RequestBody CreateProductDto createProductDto, Principal principal) {
        sellerService.addProduct(createProductDto, principal.getName());
        return new ResponseEntity<>("Product added successfully!", HttpStatus.OK);
    }

    @PatchMapping("/seller/update-product")
    public ResponseEntity<String> updateProduct(@Valid @RequestBody UpdateProductDto updateProductDto, Principal principal) {
        sellerService.updateProduct(updateProductDto, principal.getName());
        return new ResponseEntity<>("Product updated successfully!", HttpStatus.OK);
    }

    @PostMapping("/seller/add-product-variation")
    public ResponseEntity<String> addProductVariation(@Valid @RequestBody CreateProductVariationDto createProductVariationDto, Principal principal) {
        sellerService.addProductVariation(createProductVariationDto, principal.getName());
        return new ResponseEntity<>("Product Variation added successfully!", HttpStatus.OK);
    }

    @PatchMapping("/seller/update-product-variation")
    public ResponseEntity<String> updateProductVariation(@Valid @RequestBody UpdateProductVariationDto updateProductVariationDto, Principal principal) {
        sellerService.updateProductVariation(updateProductVariationDto, principal.getName());
        return new ResponseEntity<>("Product Variation updated successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/seller/delete-product/{productId}")
    public ResponseEntity<String> deleteProduct(Principal principal, @PathVariable Long productId) {
        sellerService.deleteProduct(principal.getName(), productId);
        return new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK);
    }


    private MappingJacksonValue addFilter(MappingJacksonValue mappingJacksonValue) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "brand", "active", "returnable", "description", "cancelable", "category");
        SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("productFilter", filter).addFilter("categoryFilter", filter);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping("/seller/product/{productId}")
    public MappingJacksonValue retrieveProductById(Principal principal, @PathVariable Long productId) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(sellerService.getProductById(principal.getName(), productId));
        return addFilter(mappingJacksonValue);
    }

    @GetMapping("/seller/product")
    public MappingJacksonValue retrieveAllProducts(Pageable pageable, Principal principal) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(sellerService.getAllProducts(pageable, principal.getName()));
        return addFilter(mappingJacksonValue);
    }

    @GetMapping("/seller/product-variation/{productVariationId}")
    public MappingJacksonValue retrieveProductVariationById(Principal principal, @PathVariable Long productVariationId) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(sellerService.getProductVariationById(principal.getName(), productVariationId));
        var filter1 = SimpleBeanPropertyFilter.filterOutAllExcept("id", "quantityAvailable", "price", "primaryImageName", "active", "product", "metadata");
        var filter2 = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "brand", "active", "returnable", "description", "cancelable", "category");
        var filter3 = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
        SimpleFilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("productVariationFilter", filter1)
                .addFilter("productFilter", filter2)
                .addFilter("categoryFilter", filter3);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping("/seller/product/product-variation/{productId}")
    public MappingJacksonValue retrieveAllProductVariationForProductById(Pageable pageable, Principal principal, @PathVariable Long productId) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(sellerService.getAllProductVariationForProductById(pageable, principal.getName(), productId));
        var filter1 = SimpleBeanPropertyFilter.filterOutAllExcept("id", "quantityAvailable", "price", "primaryImageName", "active", "metadata", "product");
        var filter2 = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
        SimpleFilterProvider categoryFilter = new SimpleFilterProvider()
                .addFilter("productVariationFilter", filter1)
                .addFilter("productFilter", filter2);
        mappingJacksonValue.setFilters(categoryFilter);
        return mappingJacksonValue;
    }
}
