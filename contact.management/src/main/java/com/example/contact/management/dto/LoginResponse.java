package com.example.contact.management.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private Integer id;
    private String name;
    private String email;
    private String contact;
    private String token;
}
