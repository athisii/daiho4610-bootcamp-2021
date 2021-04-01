package com.tothenew.services;

import com.tothenew.entities.user.*;
import com.tothenew.exception.*;
import com.tothenew.objects.*;
import com.tothenew.repos.AddressRepository;
import com.tothenew.repos.user.SellerRepository;
import com.tothenew.repos.user.UserRepository;
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
    private AddressRepository addressRepository;

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

    public User viewProfile(String email) {
        return userService.findUserByEmail(email);
    }

    public void updateProfile(String email, UpdateProfileDto updateProfileDto) {
        User user = userService.findUserByEmail(email);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(updateProfileDto, user);
        userRepository.save(user);
    }

    public void updatePassword(String email, ResetPasswordDto resetPasswordDto) {
        userService.updatePassword(email, resetPasswordDto);
    }

    public void updateAddress(AddressDto addressDto, Long addressId) {
        userService.updateAddress(addressDto, addressId);
    }
}
