package com.example.roomreservation.security;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import com.example.roomreservation.exception.user.UserNotFoundException;
import com.example.roomreservation.model.token.TokenInfo;
import com.example.roomreservation.model.user.User;
import com.example.roomreservation.service.TokenInfoService;
import com.example.roomreservation.service.UserService;
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

//import static com.example.roomreservation.security.LDAPServiceImpl.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthService {
    private static Hashtable<String, String> env;

    private final UserService userService;
    private final HttpServletRequest httpRequest;

    private final TokenInfoService tokenInfoService;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtils jwtTokenUtils;

    @Autowired
    public AuthService( AuthenticationManager authenticationManager,TokenInfoService tokenInfoService, JwtTokenUtils jwtTokenUtils, HttpServletRequest httpRequest,UserService userService) {
        this.authenticationManager=authenticationManager;
        this.tokenInfoService = tokenInfoService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.httpRequest = httpRequest;
        this.userService=userService;
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


    private  static ArrayList<String> getUserData(String userName, LdapContext ctx, SearchControls searchControls) {
        ArrayList<String>userData=new ArrayList<>();
        System.out.println("*** " + userName + " ***");
        User user = null;
        try {
            NamingEnumeration<SearchResult> answer = ctx.search("dc=lab,dc=local", "(&(objectClass=user)(sAMAccountName=" + userName + "))", searchControls);
            if (answer.hasMore()) {
                Attributes attrs = answer.next().getAttributes();
                 userData.add(String.valueOf(attrs.get("distinguishedName")).split(":")[1].substring(1));
                 userData.add(String.valueOf(attrs.get("mail")).split(":")[1].substring(1));
                 userData.add(String.valueOf(attrs.get("sAMAccountName")).split(":")[1].substring(1));
            } else {
                System.out.println("user not found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  userData;
    }

    private LdapContext getLdapContext() throws javax.naming.NamingException {
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
        } catch (NamingException nex) {
            System.out.println("LDAP Connection: FAILED");
            nex.printStackTrace();
        }
        return ctx;
    }

    private SearchControls getSearchControls() {
        SearchControls cons = new SearchControls();
        cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String[] attrIDs = {"distinguishedName", "sn", "givenname", "mail", "telephonenumber", "thumbnailPhoto","sAMAccountName"};
        cons.setReturningAttributes(attrIDs);
        return cons;
    }
    public JWTResponseDTO login(String userName, String password) throws javax.naming.NamingException {
        LdapContext ldapContext = getLdapContext();

        SearchControls searchControls = getSearchControls();
        // DistinguishedName is something like 'CN=test test ,CN=users,DC=lab,DC=local'
        ArrayList <String>userData = getUserData(userName, ldapContext, searchControls);
//        String mail= getMail(userName,ldapContext,searchControls);
        //for SECURITY_PRINCIPAL you should provide the DistinguishedName
        env.put(Context.SECURITY_PRINCIPAL, userData.get(0));
        env.put(Context.SECURITY_CREDENTIALS,password);
        // this will search for sMAccountName which is a small name like 'test' for the previous example
        //String sAMAccountName=getsAMAccountName(userName, ldapContext, searchControls);
        DirContext userContext = new InitialDirContext(env);
        String email=userData.get(1);
        String username=userData.get(2);
        TokenInfo tokenInfo = createLoginToken(username,email);

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
            e.printStackTrace();
        }
        String accessTokenId = UUID.randomUUID().toString();
        String accessToken = JwtTokenUtils.generateToken(username, accessTokenId, false);
        log.info("Access token created. [tokenId={}]", accessTokenId);

        String refreshTokenId = UUID.randomUUID().toString();
        String refreshToken = JwtTokenUtils.generateToken(username, refreshTokenId, true);
        log.info("Refresh token created. [tokenId={}]", accessTokenId);

        TokenInfo tokenInfo = new TokenInfo(accessToken, refreshToken);
        if (!userService.isPresentUsername(username)){
            //if the user hasn't signed in before
            tokenInfo.setUser(new com.example.roomreservation.model.user.User(username,email));
        }else
        {
            // if the user has signed in before
        User user=userService.findByUsername(username);
        user.setUsername(username);
        user.setEmail(email);
        tokenInfo.setUser(user);
        }
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