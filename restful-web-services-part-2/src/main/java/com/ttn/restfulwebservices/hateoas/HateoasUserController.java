package com.ttn.restfulwebservices.hateoas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;



import java.util.List;

@RequestMapping("/hateoas/user")
@RestController
public class HateoasUserController {
    @Autowired
    private HateoasUserService userService;

    @GetMapping
    public List<HateoasUser> retrieveAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public EntityModel<HateoasUser> retrieveUser(@PathVariable int id) {
        HateoasUser user =  userService.findById(id);
        EntityModel<HateoasUser> entityModel = EntityModel.of(user);
        Link link = linkTo(methodOn(this.getClass()).retrieveAllUsers()).withRel("user-list");
        entityModel.add(link);
        return  entityModel;
    }

}
