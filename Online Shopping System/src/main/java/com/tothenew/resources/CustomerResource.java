package com.tothenew.resources;

import com.tothenew.objects.CustomerDto;
import com.tothenew.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
        return new ResponseEntity<>("Your account verified successfully!", HttpStatus.OK);
    }

}
