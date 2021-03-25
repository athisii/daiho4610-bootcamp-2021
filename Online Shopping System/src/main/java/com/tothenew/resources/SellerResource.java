package com.tothenew.resources;

import com.tothenew.entities.user.Seller;
import com.tothenew.objects.SellerDto;
import com.tothenew.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequestMapping("/seller/registration")
@RestController
public class SellerResource {
    @Autowired
    private SellerService sellerService;


    @PostMapping
    public ResponseEntity<Object> registerSeller(@Valid @RequestBody SellerDto sellerDto) {
        Seller savedSeller = sellerService.registerNewSeller(sellerDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedSeller.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


}
