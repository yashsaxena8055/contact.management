package com.example.contact.management.controller;

import com.example.contact.management.constant.ApiUriConstant;
import com.example.contact.management.dto.CreateContactRequest;
import com.example.contact.management.dto.FetchContactsRequest;
import com.example.contact.management.dto.ResponseObj;
import com.example.contact.management.dto.UpdateContactRequest;
import com.example.contact.management.service.CommonUtilityService;
import com.example.contact.management.service.ContactService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUriConstant.CONTACT_BASE_URI)
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping(ApiUriConstant.CREATE_CONTACT)
    public ResponseEntity<ResponseObj> createContact(@RequestBody CreateContactRequest request){
        ResponseObj responseObj = contactService.create(request);
        return CommonUtilityService.setResponseEntity(responseObj);
    }

    @PostMapping(ApiUriConstant.FETCH_CONTACTS)
    public ResponseEntity<ResponseObj> fetchContacts(@RequestBody FetchContactsRequest request){
        ResponseObj responseObj = contactService.fetchContacts(request);
        return CommonUtilityService.setResponseEntity(responseObj);
    }
    @GetMapping(ApiUriConstant.DELETE_CONTACT_BY_CODE)
    public ResponseEntity<ResponseObj> deleteContact(@PathVariable("userId") Integer userId , @PathVariable("contactCode") String contactCode){
        ResponseObj responseObj = contactService.deleteContact(userId,contactCode);
        return CommonUtilityService.setResponseEntity(responseObj);
    }

    @GetMapping(ApiUriConstant.FETCH_CONATCT_BY_CODE)
    public ResponseEntity<ResponseObj> fetchByCode(@PathVariable("userId") Integer userId , @PathVariable("contactCode") String contactCode){
        ResponseObj responseObj = contactService.fetchByCode(userId,contactCode);
        return CommonUtilityService.setResponseEntity(responseObj);
    }
    @PostMapping(ApiUriConstant.UPDATE_CONTACT_BY_CODE)
    public ResponseEntity<ResponseObj> update(@RequestBody UpdateContactRequest request){
        ResponseObj responseObj = contactService.updateContact(request);
        return CommonUtilityService.setResponseEntity(responseObj);
    }

}