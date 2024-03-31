package com.mukund.Blogbackend.controller;

import com.mukund.Blogbackend.payload.Logindto;
import com.mukund.Blogbackend.payload.Registerdto;
import com.mukund.Blogbackend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

//    build login api


@PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Logindto logindto){
        String response = service.login(logindto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Registerdto registerdto){
        String response = service.register(registerdto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
