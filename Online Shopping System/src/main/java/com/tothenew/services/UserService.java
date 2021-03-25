package com.tothenew.services;

import com.tothenew.entities.token.VerificationToken;
import com.tothenew.entities.user.User;
import com.tothenew.repos.UserRepository;
import com.tothenew.repos.VerificationTokenRepository;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.UUID;


@Service
@Transactional
public class UserService {

    @Value("${admin.email.id}")
    String adminEmailId;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;


    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";


    public String createVerificationToken(final User user) {
        final String token = UUID.randomUUID().toString();
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
        return myToken.getToken();
    }

    public void sendActivationStatus(User registeredUser, String userType) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(registeredUser.getEmail());
        mailMessage.setFrom(adminEmailId);

        if (userType.equals("CUSTOMER")) {
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/customer/confirm-account?token=" + createVerificationToken(registeredUser));
        } else {
            mailMessage.setSubject("Waiting for approval!");
            mailMessage.setText("Your account has been created successfully, waiting for approval!");
        }
        emailService.sendEmail(mailMessage);
    }


    public User getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
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
            return TOKEN_EXPIRED;
        }

        user.setActive(true);
        tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }


}
