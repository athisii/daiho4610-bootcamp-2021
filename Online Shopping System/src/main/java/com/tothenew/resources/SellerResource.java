package com.tothenew.resources;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.tothenew.objects.*;
import com.tothenew.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@RestController
public class SellerResource {
    @Autowired
    private SellerService sellerService;

    @PostMapping("/registration/seller")
    public ResponseEntity<?> registerSeller(@Valid @RequestBody SellerDto sellerDto) {
        sellerService.registerNewSeller(sellerDto);
        return new ResponseEntity<>("Your account has been created successfully, waiting for approval!", HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody EmailDto emailDto) {
        sellerService.resendToken(emailDto);
        return new ResponseEntity<>("Link to reset the password has been sent.", HttpStatus.OK);
    }

    @GetMapping("/seller/profile")
    public MappingJacksonValue profile(Principal user) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(sellerService.viewProfile(user.getName()));
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "firstName", "lastName", "imageUrl", "companyContact", "companyName", "active", "addresses");
        mappingJacksonValue.setFilters(filter(filter));
        return mappingJacksonValue;
    }

    @PutMapping("/seller/profile")
    public ResponseEntity<?> updateProfile(@Valid Principal user, @RequestBody UpdateProfileDto updateProfileDto) {
        sellerService.updateProfile(user.getName(), updateProfileDto);
        return new ResponseEntity<>("Profile updated successfully!", HttpStatus.OK);
    }

    @PutMapping("/seller/update-password")
    public ResponseEntity<String> updatePassword(@Valid Principal user, @RequestBody ResetPasswordDto resetPasswordDto) {
        sellerService.updatePassword(user.getName(), resetPasswordDto);
        return new ResponseEntity<>("Password updated successfully!", HttpStatus.OK);
    }

    @PutMapping("/seller/update-address")
    public ResponseEntity<String> updateAddress(@Valid @RequestBody AddressDto addressDto, @RequestParam Long addressId) {
        sellerService.updateAddress(addressDto, addressId);
        return new ResponseEntity<>("Address updated successfully!", HttpStatus.OK);
    }

    private FilterProvider filter(SimpleBeanPropertyFilter filter) {
        return new SimpleFilterProvider().addFilter("userFilter", filter);
    }
}
