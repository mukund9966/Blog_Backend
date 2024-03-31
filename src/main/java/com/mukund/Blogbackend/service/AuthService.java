package com.mukund.Blogbackend.service;

import com.mukund.Blogbackend.payload.Logindto;
import com.mukund.Blogbackend.payload.Registerdto;

public interface AuthService {
    String login(Logindto logindto);
    String register(Registerdto registerdto);
}
