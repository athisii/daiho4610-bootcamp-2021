package com.tothenew.resources;

import com.tothenew.entities.user.Address;
import com.tothenew.entities.user.User;
import com.tothenew.objects.*;
import com.tothenew.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/customer")
@RestController
public class CustomerResource {
    @Autowired
    private CustomerService customerService;

    //Customer Registration
    @PostMapping("/registration")
    public ResponseEntity<Object> registerCustomer(@Valid @RequestBody CustomerDto customerDto, final HttpServletRequest request) {
        customerService.registerNewCustomer(customerDto);
        return new ResponseEntity<>("Your account has been created successfully, please check your email for activation.", HttpStatus.OK);
    }

    // Customer activation - verification
    @PutMapping("/confirm-account")
    public ResponseEntity<?> confirmRegistration(@RequestParam("token") String token) {
        customerService.confirmRegisteredCustomer(token);
        return new ResponseEntity<>("Your account has been verified successfully!", HttpStatus.OK);
    }

    @PostMapping("/resend-token")
    public ResponseEntity<?> resendRegistrationToken(@Valid @RequestBody EmailDto emailDto) {
        customerService.resendToken(emailDto);
        return new ResponseEntity<>("Activation link has been resent.", HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody EmailDto emailDto) {
        customerService.resetPassword(emailDto);
        return new ResponseEntity<>("Link to reset the password has been sent.", HttpStatus.OK);
    }

    @PutMapping("/confirm-reset-password")
    public ResponseEntity<?> confirmResetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto, @RequestParam("token") String token) {
        customerService.confirmResetPassword(resetPasswordDto, token);
        return new ResponseEntity<>("Password reset successfully!", HttpStatus.OK);
    }

    @GetMapping("/profile")
    public User viewProfile(Principal user) {
        return customerService.viewProfile(user.getName());

    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@Valid Principal user, @RequestBody UpdateProfileDto updateProfileDto) {
        customerService.updateProfile(user.getName(), updateProfileDto);
        return new ResponseEntity<>("Profile updated successfully!", HttpStatus.OK);


    }

    @GetMapping("/address")
    public List<Address> addresses(Principal user) {
        return customerService.addresses(user.getName());

    }

    @PostMapping("/add-address")
    public ResponseEntity<String> addAddress(@Valid Principal user, @RequestBody AddressDto addressDto) {
        customerService.addAddress(user.getName(), addressDto);
        return new ResponseEntity<>("Address added successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/delete-address")
    public ResponseEntity<String> removeAddress(@RequestParam Long addressId) {
        customerService.deleteAddress(addressId);
        return new ResponseEntity<>("Address deleted successfully!", HttpStatus.OK);
    }

    @PutMapping("/update-address")
    public ResponseEntity<String> updateAddress(@Valid @RequestBody AddressDto addressDto, @RequestParam Long addressId) {
        customerService.updateAddress(addressDto, addressId);
        return new ResponseEntity<>("Address updated successfully!", HttpStatus.OK);
    }

    //Common to all user.
    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@Valid Principal user, @RequestBody ResetPasswordDto resetPasswordDto) {
        customerService.updatePassword(user.getName(), resetPasswordDto);
        return new ResponseEntity<>("Password updated successfully!", HttpStatus.OK);
    }


//    @GetMapping("/profile")
//    public MappingJacksonValue profile(Principal user) {
//        System.out.println(user.getName());
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerService.profile(user.getName()));
//        mappingJacksonValue.setFilters(filter());
//        return mappingJacksonValue;
//    }



//    @PostMapping("/dynamic")
//    public MappingJacksonValue createDynamicFilterUser(@RequestBody DynamicFilterUser user) {
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(filterUserService.saveDynamicFilterUser(user));
//        mappingJacksonValue.setFilters(filter());
//        return mappingJacksonValue;
//    }

//    private FilterProvider filter() {
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "firstName", "lastName", "contact", "isActive");
//        return new SimpleFilterProvider().addFilter("UserFilter", filter);
//    }


}
