package com.tothenew.resources;

import com.tothenew.objects.EmailDto;
import com.tothenew.objects.ResetPasswordDto;
import com.tothenew.objects.SellerDto;
import com.tothenew.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/seller/registration")
@RestController
public class SellerResource {
    @Autowired
    private SellerService sellerService;


    @PostMapping
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




}
