package com.tothenew.resources;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.tothenew.entities.product.Category;
import com.tothenew.objects.PagingAndSortingDto;
import com.tothenew.objects.ResetPasswordDto;
import com.tothenew.repos.user.UserRepository;
import com.tothenew.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/")
@RestController
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @GetMapping
    public String home() {
        return "Welcome to Online Shopping System";
    }


    @PutMapping("/reset-password/confirm")
    public ResponseEntity<?> confirmResetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto, @RequestParam("token") String token) {
        userService.resetPassword(resetPasswordDto, token);
        return new ResponseEntity<>("Password reset successfully!", HttpStatus.OK);
    }

    @GetMapping("/category")
    public MappingJacksonValue viewAllCategories(@RequestBody PagingAndSortingDto pagingAndSortingDto) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userService.getAllCategories(pagingAndSortingDto));
        SimpleBeanPropertyFilter filter1 = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "parentCategory");
        SimpleBeanPropertyFilter filter2 = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
        SimpleFilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("categoryFilter", filter1)
                .addFilter("parentCategoryFilter", filter2);

        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }
}
