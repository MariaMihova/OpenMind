package com.OpenMind.serveces.Impl;

import com.OpenMind.models.entitis.UserEntity;
import com.OpenMind.repositories.UserRepository;
import com.OpenMind.utils.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.printf("load method was called. User name %s", username);

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found!"));

        Set<GrantedAuthority> authorities = user.
                getAuthorities().
                stream().
                map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name())).
                collect(Collectors.toUnmodifiableSet());


        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}

