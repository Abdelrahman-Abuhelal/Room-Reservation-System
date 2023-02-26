package com.example.roomreservation.security;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.roomreservation.exception.user.UserNotFoundException;
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

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthService {
    static Hashtable<String, String> env;

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
private static String getUserInfo(String userName, LdapContext ctx, SearchControls searchControls) {
    System.out.println("*** " + userName + " ***");
    User user = null;
    try {
        NamingEnumeration<SearchResult> answer = ctx.search("dc=lab,dc=local", "sAMAccountName=" + userName, searchControls);
        if (answer.hasMore()) {
            Attributes attrs = answer.next().getAttributes();
            return String.valueOf(attrs.get("distinguishedName")).split(":")[1];
        } else {
            String message="The user is not found";
            log.info(message);
           throw new UserNotFoundException(message);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return  "";
}

    private static LdapContext getLdapContext() {
        LdapContext ctx = null;
        try {
            env = new Hashtable<String, String>();

            env.put(Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://192.168.206.190:389");
            // Authenticate as S. User and password "mysecret"
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, "CN=Administrator,CN=users,DC=lab,DC=local");
            env.put(Context.SECURITY_CREDENTIALS, "Cato@1234");
            env.put(Context.REFERRAL, "follow");

            ctx = new InitialLdapContext(env, null);
            System.out.println("LDAP Connection: COMPLETE");
        } catch (NamingException | javax.naming.NamingException nex) {
            System.out.println("LDAP Connection: FAILED");
            nex.printStackTrace();
        }
        return ctx;
    }

    private static SearchControls getSearchControls() {
        SearchControls cons = new SearchControls();
        cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String[] attrIDs = {"distinguishedName", "sn", "givenname", "mail", "telephonenumber", "thumbnailPhoto"};
        cons.setReturningAttributes(attrIDs);
        return cons;
    }
    public JWTResponseDTO login(String userName, String password) throws javax.naming.NamingException {
        LdapContext ldapContext = getLdapContext();

        SearchControls searchControls = getSearchControls();
        String distinguishedName = getUserInfo(userName, ldapContext, searchControls);
        env.put(Context.SECURITY_PRINCIPAL, distinguishedName);
        env.put(Context.SECURITY_CREDENTIALS,password);
        DirContext userContext = new InitialDirContext(env);
        Attributes userAttributes = userContext.getAttributes(distinguishedName);
        String username = userAttributes.get("sAMAccountName").get().toString();
        String email = userAttributes.get("mail").get().toString();
        TokenInfo tokenInfo = createLoginToken(username, email);

        return JWTResponseDTO.builder()
                .accessToken(tokenInfo.getAccessToken())
                .refreshToken(tokenInfo.getRefreshToken())
                .build();
    }






    public TokenInfo createLoginToken(String username, String email) {
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
        tokenInfo.setUser(new com.example.roomreservation.model.user.User(username,email));
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