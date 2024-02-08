package com.lorenzhoerb.securitydemo.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Collection;

public class RobotAuthenticationProvider implements AuthenticationProvider {

    private final Collection<String> passwords;

    public RobotAuthenticationProvider(Collection<String> passwords) {
        this.passwords = passwords;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var robotAuthentication = (RobotAuthentication) authentication;
        String password = robotAuthentication.getPassword();

        if (passwords.contains(password)) {
            return RobotAuthentication.authenticated();
        } else {
            throw new BadCredentialsException("You are not the Robot");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RobotAuthentication.class.isAssignableFrom(authentication);
    }
}
