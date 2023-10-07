package com.example.contact.management.dto;

import lombok.Data;

@Data
public class CommonContactDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Integer userId;
}
