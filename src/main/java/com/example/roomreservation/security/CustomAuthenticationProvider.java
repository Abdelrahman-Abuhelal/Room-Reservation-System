package com.example.roomreservation.security;

import com.example.roomreservation.exception.user.UserNotFoundException;
import com.example.roomreservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService myuserService;

    private final AuthenticationProvider activeDirectoryLdapAuthenticationProvider;
    @Autowired

    public CustomAuthenticationProvider(UserService myuserService, AuthenticationProvider activeDirectoryLdapAuthenticationProvider) {
        super();
        this.myuserService = myuserService;
        this.activeDirectoryLdapAuthenticationProvider = activeDirectoryLdapAuthenticationProvider;
    }



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        boolean ifPresent = myuserService.findByUsername(username);

        if(ifPresent) {
            return activeDirectoryLdapAuthenticationProvider.authenticate(authentication);
        }
        else throw new UserNotFoundException("User not found.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}