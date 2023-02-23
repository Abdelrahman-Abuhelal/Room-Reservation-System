package com.example.roomreservation.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @Bean
//    public AuthenticationProvider activeDirectoryLdapAuthenticationProvider(){
//
//        ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider = new ActiveDirectoryLdapAuthenticationProvider(
//                "lab.local","ldap://192.168.206.190");
//
//// to parse AD failed credentials error message due to account - expiry,lock, credentials - expiry,lock
//        activeDirectoryLdapAuthenticationProvider.setConvertSubErrorCodesToExceptions(true);
//        return activeDirectoryLdapAuthenticationProvider;
//    }

//    @Bean
//    public LdapTemplate ldapTemplate() {
//        LdapTemplate ldapTemplate = new LdapTemplate();
//        ldapTemplate.setContextSource(getContextSource());
//        ldapTemplate.setIgnorePartialResultException(true);
//        return ldapTemplate;
//    }

//    @Bean
//    public LdapContextSource getContextSource() {
//        LdapContextSource contextSource = new LdapContextSource();
//        contextSource.setUrl("ldap://192.168.206.190:389");
//        contextSource.setBase("DC=lab,DC=local");
//        contextSource.setUserDn("administrator@lab.local");
//        contextSource.setPassword("Cato@1234");
//        contextSource.afterPropertiesSet();
//        return contextSource;
//    }




}
