package com.example.contact.management.dto;

import com.example.contact.management.constant.enums.ContactStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactDTO {
    private String contactCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Integer userId;
    private ContactStatus contactStatus;

    public ContactDTO(String contactCode,String firstName, String lastName, String email,String phoneNumber,Integer userId,ContactStatus contactStatus){
        this.contactCode = contactCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.contactStatus = contactStatus;

    }
    public ContactDTO(){}
}
