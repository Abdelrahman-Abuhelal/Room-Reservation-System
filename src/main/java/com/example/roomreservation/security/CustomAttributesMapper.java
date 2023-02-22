package com.example.roomreservation.security;

import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.directory.Attributes;

public class CustomAttributesMapper  implements AttributesMapper<Boolean> {
    @Override
    public Boolean mapFromAttributes(Attributes attributes) throws NamingException {
        return true;
    }
}
