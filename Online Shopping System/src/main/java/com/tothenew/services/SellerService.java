package com.tothenew.services;

import com.tothenew.entities.user.Role;
import com.tothenew.entities.user.Seller;
import com.tothenew.entities.user.User;
import com.tothenew.entities.user.UserRole;
import com.tothenew.exception.EmailExistsException;
import com.tothenew.exception.UserActivationException;
import com.tothenew.exception.UserAlreadyExistException;
import com.tothenew.exception.UserNotFoundException;
import com.tothenew.objects.EmailDto;
import com.tothenew.objects.ResetPasswordDto;
import com.tothenew.objects.SellerDto;
import com.tothenew.repos.SellerRepository;
import com.tothenew.repos.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class SellerService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerNewSeller(SellerDto sellerDto)
            throws UserAlreadyExistException {

        if (emailExists(sellerDto.getEmail())) {
            throw new EmailExistsException("Email already registered");
        }

        Seller newSeller = new Seller();
        ModelMapper mm = new ModelMapper();
        mm.map(sellerDto, newSeller);
        newSeller.setPassword(passwordEncoder.encode(newSeller.getPassword()));
        newSeller.setCompanyContact(sellerDto.getPhone().getValue());
        sellerDto.getCompanyAddress().setUser(newSeller);
        newSeller.setAddresses(Collections.singletonList(sellerDto.getCompanyAddress()));

        Role role_seller = userService.getRole(UserRole.SELLER);
        newSeller.getRoles().add(role_seller);

        sellerRepository.save(newSeller);
        userService.sendActivationMessage(newSeller, UserRole.SELLER);


    }

    public void resetPassword(ResetPasswordDto resetPasswordDto, String token) {
        userService.resetPassword(resetPasswordDto, token);

    }

    public void resendToken(EmailDto emailDto) throws UserNotFoundException {
        User user = userRepository.findByEmail(emailDto.getEmail());
        if (user == null) {
            throw new UserNotFoundException("No such email found!");
        }
        if (user.isActive()) {
            userService.sendResetPasswordMessage(user);
        } else {
            throw new UserActivationException("Account not activated, waiting for approval!");
        }

    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

}
