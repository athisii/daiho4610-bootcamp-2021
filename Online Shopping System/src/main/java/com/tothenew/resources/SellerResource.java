package com.tothenew.resources;

import com.tothenew.entities.user.Seller;
import com.tothenew.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/seller")
@RestController
public class SellerResource {
    @Autowired
    private UserService userService;


    @GetMapping
    public List<Seller> retrieveAllSellers() {
        return userService.getAllSellers();
    }

}
