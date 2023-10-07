package com.example.contact.management.service;

import com.example.contact.management.model.User;
import com.example.contact.management.security.JwtUtilityService;
import com.example.contact.management.security.service.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private JwtUtilityService jwtUtilityService;

    public String getJwtToken(User user){
        UserDetail userDetail = new UserDetail(user);
        String token = jwtUtilityService.generateToken(userDetail);
        return token;
    }

}

