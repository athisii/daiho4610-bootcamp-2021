package com.tothenew.resources;

import com.tothenew.entities.user.User;
import com.tothenew.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@SuppressWarnings("deprecation")
@RequestMapping("/")
@RestController
public class UserResource {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TokenStore tokenStore;

    @GetMapping
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }
    

    @GetMapping("/admin/home")
    public String adminHome() {
        return "Admin home";
    }

    @GetMapping("/doLogout")
    public String logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return "Logged out successfully";
    }


}
