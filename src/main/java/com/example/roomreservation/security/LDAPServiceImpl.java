package com.example.roomreservation.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

import org.springframework.stereotype.Component;

import javax.naming.directory.Attributes;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Component
@Slf4j
public class LDAPServiceImpl {
//        implements AuthenticationProvider {

    @Autowired
    private LdapTemplate ldapTemplate;


    //test the connection to the active directory
//    public boolean testConnection() {
//        try {
//            ldapTemplate.search("", "(objectClass=*)", new CustomAttributesMapper());
//            return true;
//        } catch (Exception ex) {
//            return false;
//        }
//    }

    public void getUserDetails(String userName) {
        if (null != ldapTemplate) {
            List<String> vals = ldapTemplate.search(query().where("objectclass").is("person"),
                    new AttributesMapper<String>() {
                        @Override
                        public String mapFromAttributes(Attributes attributes) throws NamingException, javax.naming.NamingException {
                            return attributes.get("sAMAccountName").get().toString();
                        }
                    });
            for (String s : vals) {
                log.info("attr : " + s);
            }
        } else {
            log.info("Templates is null");
        }
    }





//
//    private final UserService myuserService;
//
//    private final AuthenticationProvider activeDirectoryLdapAuthenticationProvider;
//    @Autowired
//
//    public CustomAuthenticationProvider(UserService myuserService, AuthenticationProvider activeDirectoryLdapAuthenticationProvider) {
//        super();
//        this.myuserService = myuserService;
//        this.activeDirectoryLdapAuthenticationProvider = activeDirectoryLdapAuthenticationProvider;
//    }
//
//
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//        String username = authentication.getName();
//        boolean ifPresent = myuserService.findByUsername(username);
//
//        if(ifPresent) {
//            return activeDirectoryLdapAuthenticationProvider.authenticate(authentication);
//        }
//        else throw new UserNotFoundException("User not found.");
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
}