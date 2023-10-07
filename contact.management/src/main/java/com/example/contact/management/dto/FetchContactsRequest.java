package com.example.contact.management.dto;

import lombok.Data;

@Data
public class FetchContactsRequest {
    private Integer userId;
    private Integer recordSize;
    private Integer pageNumber;
    private String searchString;
}
