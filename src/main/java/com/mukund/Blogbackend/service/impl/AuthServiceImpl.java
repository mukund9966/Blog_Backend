package com.mukund.Blogbackend.service.impl;

import com.mukund.Blogbackend.entity.Role;
import com.mukund.Blogbackend.entity.User;
import com.mukund.Blogbackend.exception.BlogAPIException;
import com.mukund.Blogbackend.payload.Logindto;
import com.mukund.Blogbackend.payload.Registerdto;
import com.mukund.Blogbackend.repository.RoleRepository;
import com.mukund.Blogbackend.repository.UserRepository;
import com.mukund.Blogbackend.security.JWTTokenProvider;
import com.mukund.Blogbackend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;

@Service

public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private JWTTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider  = jwtTokenProvider;
    }

    @Override
    public String login(Logindto logindto) {
   Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logindto.getUsernameorEmail(), logindto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        )
        return "User loggedin successfully";


    }

    @Override
    public String register(Registerdto registerdto) {
//        check if user already present in database

        if(userRepository.existsByUsername(registerdto.getUsername())){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST, "username already exist");
        }
        if(userRepository.existsByEmail(registerdto.getEmail())){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST, "email alreayd exist");
        }
        User user = new User();
        user.setName(registerdto.getName());
        user.setEmail(registerdto.getEmail());
        user.setUsername(registerdto.getUsername());
        user.setPassword(passwordEncoder.encode(registerdto.getPassword()));
        Set<Role> roleSet = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER").get();
        roleSet.add(role);
        user.setRoles(roleSet);
        userRepository.save(user);
        return "user registered successfully";
    }
}
