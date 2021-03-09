package com.ttn.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/filter")
@RestController
public class FilterController {
    @Autowired
    private FilterUserService filterUserService;

    @GetMapping("/static")
    public List<StaticFilterUser> retrieveAllStaticFilterUsers() {
        return filterUserService.getAllStaticFilterUsers();
    }

    @PostMapping("/static")
    public StaticFilterUser createStaticFilterUser(@RequestBody StaticFilterUser user) {
        return filterUserService.saveStaticFilterUser(user);
    }

    @GetMapping("/dynamic")
    public MappingJacksonValue retrieveAllDynamicFilterUsers() {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(filterUserService.getAllDynamicFilterUsers());
        mappingJacksonValue.setFilters(filter());
        return mappingJacksonValue;
    }

    @PostMapping("/dynamic")
    public MappingJacksonValue createDynamicFilterUser(@RequestBody DynamicFilterUser user) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(filterUserService.saveDynamicFilterUser(user));
        mappingJacksonValue.setFilters(filter());
        return mappingJacksonValue;
    }

    private FilterProvider filter() {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
        return new SimpleFilterProvider().addFilter("DynamicFilterUser", filter);
    }


}
