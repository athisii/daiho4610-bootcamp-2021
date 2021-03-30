package com.tothenew.resources;

import com.tothenew.entities.user.User;
import com.tothenew.objects.*;
import com.tothenew.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/seller")
@RestController
public class SellerResource {
    @Autowired
    private SellerService sellerService;

    @PostMapping("/registration")
    public ResponseEntity<?> registerSeller(@Valid @RequestBody SellerDto sellerDto) {
        sellerService.registerNewSeller(sellerDto);
        return new ResponseEntity<>("Your account has been created successfully", HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody EmailDto emailDto) {
        sellerService.resendToken(emailDto);
        return new ResponseEntity<>("Link to reset the password has been sent.", HttpStatus.OK);
    }

    @PutMapping("/confirm-reset-password/{token}")
    public ResponseEntity<?> confirmResetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto, @PathVariable String token) {
        sellerService.resetPassword(resetPasswordDto, token);
        return new ResponseEntity<>("Password reset successfully!", HttpStatus.OK);
    }

    @GetMapping("/profile")
    public User viewProfile(Principal user) {
        return sellerService.viewProfile(user.getName());

    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@Valid Principal user, @RequestBody UpdateProfileDto updateProfileDto) {
        sellerService.updateProfile(user.getName(), updateProfileDto);
        return new ResponseEntity<>("Profile updated successfully!", HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@Valid Principal user, @RequestBody ResetPasswordDto resetPasswordDto) {
        sellerService.updatePassword(user.getName(), resetPasswordDto);
        return new ResponseEntity<>("Password updated successfully!", HttpStatus.OK);
    }

    @PutMapping("/update-address")
    public ResponseEntity<String> updateAddress(@Valid @RequestBody AddressDto addressDto, @RequestParam Long addressId) {
        sellerService.updateAddress(addressDto, addressId);
        return new ResponseEntity<>("Address updated successfully!", HttpStatus.OK);
    }
}
