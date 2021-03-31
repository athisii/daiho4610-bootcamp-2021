package com.tothenew.resources;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.tothenew.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/admin")
@RestController
public class AdminResource {

    @Autowired
    private UserService userService;

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

    private FilterProvider filter() {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAll();
        return new SimpleFilterProvider().addFilter("userFilter", filter);
    }


}
