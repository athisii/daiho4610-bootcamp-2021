package com.tothenew.services;

import com.tothenew.entities.token.VerificationToken;
import com.tothenew.entities.user.Customer;
import com.tothenew.entities.user.Role;
import com.tothenew.entities.user.User;
import com.tothenew.entities.user.UserRole;
import com.tothenew.exception.EmailExistsException;
import com.tothenew.exception.InvalidTokenException;
import com.tothenew.exception.UserActivationException;
import com.tothenew.exception.UserNotFoundException;
import com.tothenew.objects.CustomerDto;
import com.tothenew.objects.EmailDto;
import com.tothenew.objects.ResetPasswordDto;
import com.tothenew.repos.CustomerRepository;
import com.tothenew.repos.UserRepository;
import com.tothenew.repos.VerificationTokenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerService {
    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerNewCustomer(CustomerDto customerDto)
            throws EmailExistsException {

        if (emailExists(customerDto.getEmail())) {
            throw new EmailExistsException("Email already registered");
        }
        Customer newCustomer = new Customer();
        ModelMapper mm = new ModelMapper();
        mm.map(customerDto, newCustomer);
        newCustomer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
        Role role_customer = userService.getRole(UserRole.CUSTOMER);
        newCustomer.getRoles().add(role_customer);
        customerRepository.save(newCustomer);
        userService.sendActivationMessage(newCustomer, UserRole.CUSTOMER);
    }

    public void confirmRegisteredCustomer(String token) throws InvalidTokenException {
        //If token got expired, new token will be generated and resend to the user.
        String result = userService.validateVerificationToken(token);
        if (result.equals("invalidToken")) {
            throw new InvalidTokenException("Invalid Token");
        }
        User user = userService.getUser(token);
        user.setActive(true);
        userRepository.save(user);
        userService.sendActivatedMessage(user.getEmail());
        VerificationToken oldToken = verificationTokenRepository.findByToken(token);
        verificationTokenRepository.delete(oldToken);

    }

    public void resendToken(EmailDto emailDto) throws UserNotFoundException {
        User user = userRepository.findByEmail(emailDto.getEmail());
        if (user == null) {
            throw new UserNotFoundException("No such email found!");
        }
        VerificationToken oldToken = verificationTokenRepository.findTokenByUserId(user.getId());
        verificationTokenRepository.delete(oldToken);
        userService.sendActivationMessage(user, UserRole.CUSTOMER);

    }


    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public void confirmResetPassword(ResetPasswordDto resetPasswordDto, String token) {
        userService.resetPassword(resetPasswordDto, token);

    }

    public void resetPassword(EmailDto emailDto) {
        User user = userRepository.findByEmail(emailDto.getEmail());
        if (user == null) {
            throw new UserNotFoundException("No such email found!");
        }
        if (!user.isActive()) {
            throw new UserActivationException("User not activated");
        }
        userService.sendResetPasswordMessage(user);
    }
}

