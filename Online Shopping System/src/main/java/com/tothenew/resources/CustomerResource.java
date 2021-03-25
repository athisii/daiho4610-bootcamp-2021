package com.tothenew.resources;

import com.tothenew.entities.user.Customer;
import com.tothenew.objects.CustomerDto;
import com.tothenew.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequestMapping("/customer/registration")
@RestController
public class CustomerResource {
    @Autowired
    private CustomerService customerService;


    @PostMapping
    public ResponseEntity<Object> registerCustomer(@Valid @RequestBody CustomerDto customerDto) {
        Customer savedCustomer = customerService.registerNewCustomer(customerDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCustomer.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

//    @GetMapping("/dynamic")
//    public MappingJacksonValue retrieveAllDynamicFilterUsers() {
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(filterUserService.getAllDynamicFilterUsers());
//        mappingJacksonValue.setFilters(filter());
//        return mappingJacksonValue;
//    }
//
//    @PostMapping("/dynamic")
//    public MappingJacksonValue createDynamicFilterUser(@RequestBody DynamicFilterUser user) {
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(filterUserService.saveDynamicFilterUser(user));
//        mappingJacksonValue.setFilters(filter());
//        return mappingJacksonValue;
//    }
//
//    private FilterProvider filter() {
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
//        return new SimpleFilterProvider().addFilter("DynamicFilterUser", filter);
//    }

}
