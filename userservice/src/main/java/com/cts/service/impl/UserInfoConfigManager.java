package com.cts.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.entity.User;
import com.cts.repository.AuthRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserInfoConfigManager implements UserDetailsService {

    private final AuthRepository authRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = authRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        if(user.isStatus() == false) {
            throw new UsernameNotFoundException("Invalid email or password");
        }

        String dbRole = user.getRole();
        String normalizedRole;

        if (dbRole == null) {
            normalizedRole = "USER";
        } else if (dbRole.equalsIgnoreCase("deliveryPartner")) {
            normalizedRole = "DELIVERY_PARTNER";
        } else if (dbRole.equalsIgnoreCase("customer")) {
            normalizedRole = "CUSTOMER";
        } else {
            normalizedRole = dbRole.toUpperCase();
        }

        String authority = "ROLE_" + normalizedRole;

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authority)
                .build();
    }
}