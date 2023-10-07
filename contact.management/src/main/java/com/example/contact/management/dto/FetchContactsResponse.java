package com.example.contact.management.dto;

import com.example.contact.management.model.Contact;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FetchContactsResponse {
    private List<ContactDTO> contactList = new ArrayList<>();
}
