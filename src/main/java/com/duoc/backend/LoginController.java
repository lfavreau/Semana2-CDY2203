package com.duoc.backend;
import com.duoc.backend.JWTAuthenticationConfig;
import com.duoc.backend.user.MyUserDetailsService;
import com.duoc.backend.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//agrego dependencias faltantes
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.util.HtmlUtils;


@RestController
public class LoginController {

    @Autowired
    JWTAuthenticationConfig jwtAuthtenticationConfig;

    @Autowired
    private MyUserDetailsService userDetailsService;

    // agrego autowire passencoder
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {
        // cargar usuario
        final UserDetails userDetails =
            userDetailsService.loadUserByUsername(loginRequest.getUsername());

        // comparar con BCrypt no equals plano
        if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            throw new RuntimeException("Invalid login");
        }

        // sin carácteres extraños
        String safeUsername = HtmlUtils.htmlEscape(userDetails.getUsername());
        return jwtAuthtenticationConfig.getJWTToken(safeUsername);
    }
}