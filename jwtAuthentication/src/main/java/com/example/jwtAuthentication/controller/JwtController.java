package com.example.jwtAuthentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jwt")
public class JwtController {

    @GetMapping("/check")
    public String check(){
        return "working fine";
    }
}
