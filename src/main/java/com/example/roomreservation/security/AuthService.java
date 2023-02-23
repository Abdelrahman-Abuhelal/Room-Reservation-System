package com.example.roomreservation.security;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.roomreservation.model.token.TokenInfo;
import com.example.roomreservation.model.user.User;
import com.example.roomreservation.service.TokenInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final HttpServletRequest httpRequest;

    private final TokenInfoService tokenInfoService;

    private final JwtTokenUtils jwtTokenUtils;

    @Autowired
    public AuthService(AuthenticationManager authManager,TokenInfoService tokenInfoService, JwtTokenUtils jwtTokenUtils, HttpServletRequest httpRequest) {
        this.authManager = authManager;
        this.tokenInfoService = tokenInfoService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.httpRequest = httpRequest;
    }

//    public JWTResponseDTO login(String username, String password) {
////        log.info(login+"  "+ password);
//        log.debug("reached here");
//        Authentication authentication = authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password));
//
//        log.debug("Valid userDetails credentials.");
//
//        com.example.roomreservation.model.user.User userDetails = (User) authentication.getPrincipal();
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        log.debug("SecurityContextHolder updated. [login={}]", username);
//
//
//        TokenInfo tokenInfo = createLoginToken(username, userDetails.getId());
//
//
//        return JWTResponseDTO.builder()
//                .accessToken(tokenInfo.getAccessToken())
//                .refreshToken(tokenInfo.getRefreshToken())
//                .build();
//    }
    public String login(String username, String password) {

        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://192.168.206.190:389");
        contextSource.setBase("cn=administrator,cn=users,DC=lab,DC=local");
        contextSource.setUserDn("lab\\administrator");
        contextSource.setPassword("Cato@1234");
        contextSource.setReferral("follow");
        contextSource.afterPropertiesSet();
        try {
            contextSource.afterPropertiesSet();
            DirContext dirContext = contextSource.getContext(contextSource.getUserDn(), contextSource.getPassword());
            if (dirContext != null) {
                log.info("Connection to Active Directory successful.");
            }
        } catch (Exception e) {
            System.out.println("Connection to Active Directory failed: " + e.getMessage());
        }
        BindAuthenticator authenticator = new BindAuthenticator(contextSource);

        authenticator.setUserSearch(new FilterBasedLdapUserSearch("dc=lab,dc=local", "sAMAccountName="+username, contextSource));
        Authentication authentication = null;
        try {
            authentication = (Authentication) authenticator.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        } catch (BadCredentialsException e) {
        }

        if (authentication != null) {
            String userFullName = "";
            AttributesMapper<String> attributesMapper = new AttributesMapper<String>() {
                @Override
                public String mapFromAttributes(Attributes attributes) throws NamingException, javax.naming.NamingException {
                    Attribute cnAttr = attributes.get("cn");
                    if (cnAttr != null) {
                        return (String) cnAttr.get();
                    } else {
                        return null;
                    }
                }
            };
            LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
            ldapTemplate.setIgnorePartialResultException(true);
            List<String> userFullNames = ldapTemplate.search("dc=lab,dc=local", "(sAMAccountName=" + username + ")", attributesMapper);
            if (!userFullNames.isEmpty()) {
                userFullName = userFullNames.get(0);
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);

//            TokenInfo tokenInfo = createLoginToken(username, userFullName);

            return userFullName;
        } else {
            throw new BadCredentialsException("Authentication failed");
        }
//        com.example.roomreservation.model.user.User userDetails = (User) authentication.getPrincipal();
//
//        // Update the security context
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Create login token
//        TokenInfo tokenInfo = createLoginToken(username, userDetails.getId());

        // Return JWTResponseDTO
    }



    public TokenInfo createLoginToken(String username, Long userId) {
        String userAgent = httpRequest.getHeader(HttpHeaders.USER_AGENT);
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String accessTokenId = UUID.randomUUID().toString();
        String accessToken = JwtTokenUtils.generateToken(username, accessTokenId, false);
        log.info("Access token created. [tokenId={}]", accessTokenId);

        String refreshTokenId = UUID.randomUUID().toString();
        String refreshToken = JwtTokenUtils.generateToken(username, refreshTokenId, true);
        log.info("Refresh token created. [tokenId={}]", accessTokenId);

        TokenInfo tokenInfo = new TokenInfo(accessToken, refreshToken);
        tokenInfo.setUser(new com.example.roomreservation.model.user.User(userId));
        tokenInfo.setUserAgentText(userAgent);
        tokenInfo.setLocalIpAddress(ip.getHostAddress());
        tokenInfo.setRemoteIpAddress(httpRequest.getRemoteAddr());
        // tokenInfo.setLoginInfo(createLoginInfoFromRequestUserAgent());
        return tokenInfoService.saveToken(tokenInfo);
    }


    public AccessTokenDto refreshAccessToken(String refreshToken) {
        if (jwtTokenUtils.isTokenExpired(refreshToken)) {
            return null;
        }
        String userName = jwtTokenUtils.getUserNameFromToken(refreshToken);
        Optional<TokenInfo> refresh = tokenInfoService.findByRefreshToken(refreshToken);
        if (!refresh.isPresent()) {
            return null;
        }

        return new AccessTokenDto(JwtTokenUtils.generateToken(userName, UUID.randomUUID().toString(), false));

    }


    public void logoutUser(String refreshToken) {
        Optional<TokenInfo> tokenInfo = tokenInfoService.findByRefreshToken(refreshToken);
        if (tokenInfo.isPresent()) {
            tokenInfoService.deleteById(tokenInfo.get().getId());
        }

    }

}