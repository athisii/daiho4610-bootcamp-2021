package com.ttn.restfulwebservices.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


// http://localhost:8080/v2/api-docs
// http://localhost:8080/swagger-ui/

@RequestMapping("/swagger")
@RestController
public class SwaggerUserController {
    @Autowired
    SwaggerUserService userService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Finds user by id",
            notes = "Provide an id to look up specific user from the users list",
            response = SwaggerUser.class)
    public SwaggerUser retrieveUser(@PathVariable int id) {
        return userService.getUserBy(id);
    }

    @PostMapping
    @ApiOperation(value = "Saves details of user",
            notes = "Provide user details in the Request body")
    public void createUser(@RequestBody SwaggerUser user) {
        userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes a user",
            notes = "Provide an id of the user to be deleted")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
    }
}
