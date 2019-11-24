package com.app.events.controller;

import java.util.Arrays;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
public class authConfig {
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        UserDetails basicActiveUser =
            new org.springframework.security.core.userdetails.User("dusan","bucan",
                Arrays.asList(
                        new SimpleGrantedAuthority("ROLE_REGULAR")
                )
            );
        UserDetails adminActiveUser =
            new org.springframework.security.core.userdetails.User("milovica","cao",
                Arrays.asList(
                        new SimpleGrantedAuthority("ROLE_ADMIN")
                )
            );
        return new InMemoryUserDetailsManager(
            Arrays.asList(basicActiveUser, adminActiveUser)
        );
    }
}