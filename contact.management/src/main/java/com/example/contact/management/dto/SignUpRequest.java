package com.example.contact.management.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String name;
    private String username;
    private String password;
    private String contact;
    private String email;
}
