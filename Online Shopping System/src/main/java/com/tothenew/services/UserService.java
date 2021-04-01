package com.tothenew.services;

import com.tothenew.entities.token.VerificationToken;
import com.tothenew.entities.user.*;
import com.tothenew.exception.AddressNotFoundException;
import com.tothenew.exception.InvalidTokenException;
import com.tothenew.exception.UserActivationException;
import com.tothenew.exception.UserNotFoundException;
import com.tothenew.objects.AddressDto;
import com.tothenew.objects.ResetPasswordDto;
import com.tothenew.repos.AddressRepository;
import com.tothenew.repos.RoleRepository;
import com.tothenew.repos.user.UserRepository;
import com.tothenew.repos.VerificationTokenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
@Transactional
public class UserService {

    @Value("${admin.email.id}")
    String adminEmailId;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_VALID = "valid";

    public static final int MAX_FAILED_ATTEMPTS = 3;

    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours


    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public Role getRole(UserRole userRole) {
        return roleRepository.findByAuthority("ROLE_" + userRole.name());
    }

    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        userRepository.updateFailedAttempts(newFailAttempts, user.getEmail());
    }

    public void resetFailedAttempts(String email) {
        userRepository.updateFailedAttempts(0, email);
    }

    public void lock(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());

        userRepository.save(user);
    }

    public boolean unlockWhenTimeExpired(User user) {
        long lockTimeInMillis = user.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);
            userRepository.save(user);
            return true;
        }

        return false;
    }


    //Token Services
    public String createVerificationToken(final User user) {
        final String token = UUID.randomUUID().toString();
        final VerificationToken myToken = new VerificationToken(token, user);
        verificationTokenRepository.save(myToken);
        return myToken.getToken();
    }


    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            this.sendActivationMessage(user, UserRole.CUSTOMER);
            throw new InvalidTokenException("Token expired. New activation link sent to the registered email id.");
        }
        return TOKEN_VALID;
    }

    public User getUser(final String verificationToken) {
        final VerificationToken token = verificationTokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    //Messages Services

    public void sendActivationMessage(User registeredUser, UserRole userRole) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(registeredUser.getEmail());
        mailMessage.setFrom(adminEmailId);
        if (UserRole.CUSTOMER == userRole) {
            mailMessage.setSubject("Complete Your Registration!");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/register/customer/confirm-account?token=" + createVerificationToken(registeredUser));
        } else {
            mailMessage.setSubject("Waiting for approval!");
            mailMessage.setText("Your account has been created successfully, waiting for approval!");
        }
        emailService.sendEmail(mailMessage);
    }


    public void sendActivatedMessage(String userEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(userEmail);
        mailMessage.setFrom(adminEmailId);
        mailMessage.setSubject("Account Activated!");
        mailMessage.setText("Your account has been activated successfully! You can now login.");
        emailService.sendEmail(mailMessage);
    }

    public void sendDeactivatedMessage(String userEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(userEmail);
        mailMessage.setFrom(adminEmailId);
        mailMessage.setSubject("Account Deactivated!");
        mailMessage.setText("Your account has been deactivated!");
        emailService.sendEmail(mailMessage);
    }

    public void sendResetPasswordMessage(User registeredUser) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(registeredUser.getEmail());
        mailMessage.setFrom(adminEmailId);
        mailMessage.setSubject("Please reset your password");
        mailMessage.setText("To reset your password, please click here : "
                + "http://localhost:8080/reset-password/confirm?token=" + createVerificationToken(registeredUser));
        emailService.sendEmail(mailMessage);
    }

    public void sendResetSuccessMessage(String userEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setFrom(adminEmailId);
        mailMessage.setSubject("Password Reset Successfully");
        mailMessage.setText("Your password has been reset successfully!");
        emailService.sendEmail(mailMessage);
    }


    public List<Customer> getAllCustomers() {
        return userRepository.findAllCustomers();
    }

    public List<Seller> getAllSellers() {
        return userRepository.findAllSellers();
    }

    public void activateUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.orElseThrow(() -> new UserNotFoundException("No user found for id: " + userId));
        userOptional.ifPresent(user -> {
            if (user.isActive()) {
                throw new UserActivationException("User already activated");
            }
            user.setActive(true);
            userRepository.save(user);
            sendActivatedMessage(user.getEmail());

        });
    }

    public void deactivateUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.orElseThrow(() -> new UserNotFoundException("No user found for id: " + userId));
        userOptional.ifPresent(user -> {
            if (!user.isActive()) {
                throw new UserActivationException("User already deactivated");
            }
            user.setActive(false);
            userRepository.save(user);
            sendDeactivatedMessage(user.getEmail());
        });
    }

    public void resetPassword(ResetPasswordDto resetPasswordDto, String token) {
        String result = validateVerificationToken(token);
        if (result.equals("invalidToken")) {
            throw new InvalidTokenException("Invalid Token");
        }
        User user = getUser(token);
        user.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
        userRepository.save(user);
        sendResetSuccessMessage(user.getEmail());
        VerificationToken oldToken = verificationTokenRepository.findByToken(token);
        verificationTokenRepository.delete(oldToken);
    }

    public void sendLockedMessage(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom(adminEmailId);
        mailMessage.setSubject("Account Locked");
        mailMessage.setText("Your account has been locked due to 3 failed attempts. It will be unlocked after 24 hours.");
        emailService.sendEmail(mailMessage);
    }

    public void sendUnLockedMessage(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom(adminEmailId);
        mailMessage.setSubject("Account Unlocked");
        mailMessage.setText("Your account has been unlocked. Please try to login again.");
        emailService.sendEmail(mailMessage);
    }

    public void updatePassword(String email, ResetPasswordDto resetPasswordDto) {
        User user = findUserByEmail(email);
        user.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
        sendResetSuccessMessage(email);
        userRepository.save(user);
    }

    public void updateAddress(AddressDto addressDto, Long addressId) {
        Optional<Address> addressOptional = addressRepository.findById(addressId);
        addressOptional.orElseThrow(() -> new AddressNotFoundException("Address not found for id: " + addressId));
        addressOptional.ifPresent(address -> {
            ModelMapper mm = new ModelMapper();
            mm.map(addressDto, address);
            addressRepository.save(address);
        });
    }
}
