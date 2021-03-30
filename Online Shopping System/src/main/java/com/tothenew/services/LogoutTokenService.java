package com.tothenew.services;

import com.tothenew.entities.token.LogoutToken;
import com.tothenew.repos.LogoutTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutTokenService {
    @Autowired
    private LogoutTokenRepository logoutTokenRepository;

    public void saveLogoutToken(String token) {
        logoutTokenRepository.save(new LogoutToken(token));
    }

    public boolean isBlacklisted(String token) {
        return logoutTokenRepository.findByToken(token) != null;
    }

}
