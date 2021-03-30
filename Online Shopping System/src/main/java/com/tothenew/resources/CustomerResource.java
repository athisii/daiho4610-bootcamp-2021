package com.tothenew.resources;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.tothenew.entities.user.Customer;
import com.tothenew.entities.user.User;
import com.tothenew.objects.CustomerDto;
import com.tothenew.objects.EmailDto;
import com.tothenew.objects.ResetPasswordDto;
import com.tothenew.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

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
    public User profile(Principal user) {
        System.out.println(user.getName());
        return customerService.profile(user.getName());

    }
// @GetMapping("/{customerId}/profile")
//    public MappingJacksonValue profile(@PathVariable Long customerId) {
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(customerService.profile(customerId));
//        mappingJacksonValue.setFilters(filter());
//        return mappingJacksonValue;
//    }

//    @GetMapping("/dynamic")
//    public MappingJacksonValue retrieveAllDynamicFilterUsers() {
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(filterUserService.getAllDynamicFilterUsers());
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
