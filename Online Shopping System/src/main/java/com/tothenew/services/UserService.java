package com.tothenew.services;

import com.tothenew.entities.token.VerificationToken;
import com.tothenew.entities.user.*;
import com.tothenew.exception.InvalidTokenException;
import com.tothenew.exception.UserActivationException;
import com.tothenew.exception.UserNotFoundException;
import com.tothenew.objects.ResetPasswordDto;
import com.tothenew.repos.RoleRepository;
import com.tothenew.repos.UserRepository;
import com.tothenew.repos.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
public class UserService {

    @Value("${admin.email.id}")
    String adminEmailId;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_VALID = "valid";


    public Role getRole(UserRole userRole) {
        return roleRepository.findByAuthority("ROLE_" + userRole.name());
    }


    public String createVerificationToken(final User user) {
        final String token = UUID.randomUUID().toString();
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
        return myToken.getToken();
    }

    public void sendActivationMessage(User registeredUser, UserRole userRole) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(registeredUser.getEmail());
        mailMessage.setFrom(adminEmailId);
        if (UserRole.CUSTOMER == userRole) {
            mailMessage.setSubject("Complete Your Registration!");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/customer/confirm-account?token=" + createVerificationToken(registeredUser));
        } else {
            mailMessage.setSubject("Waiting for approval!");
            mailMessage.setText("Your account has been created successfully, waiting for approval!");
        }
        emailService.sendEmail(mailMessage);
    }


    public void sendResetPasswordMessage(User registeredUser) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(registeredUser.getEmail());
        mailMessage.setFrom(adminEmailId);
        mailMessage.setSubject("Reset Your Password!");
        mailMessage.setText("To reset your password, please click here : "
                + "http://localhost:8080/customer/confirm-account?token=" + createVerificationToken(registeredUser));
        emailService.sendEmail(mailMessage);
    }


    public String validateVerificationTokenAndSaveUser(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            this.sendActivationMessage(user, UserRole.CUSTOMER);
            throw new InvalidTokenException("Token expired. New activation link sent to the registered email id.");
        }
        //only for registration confirmation.
        if (!user.isActive()) {
            user.setActive(true);
            tokenRepository.delete(verificationToken);
            userRepository.save(user);
            sendActivatedMessage(user.getEmail());
        }
        return TOKEN_VALID;
    }

    public User getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
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

    public List<Customer> getAllCustomers() {
        return userRepository.findAllCustomers();
    }

    public List<Seller> getAllSellers() {
        return userRepository.findAllSellers();
    }

    public void activateUserById(int userId) {
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

    public void deactivateUserById(int userId) {
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
        String result = validateVerificationTokenAndSaveUser(token);
        if (result.equals("invalidToken")) {
            throw new InvalidTokenException("Invalid Token");
        }
        User user = getUser(token);
        user.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
        userRepository.save(user);
    }
}
