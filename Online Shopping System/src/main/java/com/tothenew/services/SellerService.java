package com.tothenew.services;

import com.tothenew.entities.user.Role;
import com.tothenew.entities.user.Seller;
import com.tothenew.exception.EmailExistsException;
import com.tothenew.exception.UserAlreadyExistException;
import com.tothenew.objects.SellerDto;
import com.tothenew.repos.SellerRepository;
import com.tothenew.repos.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SellerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Seller registerNewSeller(SellerDto sellerDto)
            throws UserAlreadyExistException {

        if (emailExists(sellerDto.getEmail())) {
            throw new EmailExistsException("Email already registered");
        }

        Seller newSeller = new Seller();
        ModelMapper mm = new ModelMapper();
        mm.map(sellerDto, newSeller);
        newSeller.setPassword(passwordEncoder.encode(newSeller.getPassword()));
        newSeller.getRoles().add(new Role("ROLE_SELLER"));
        return sellerRepository.save(newSeller);

    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

}
