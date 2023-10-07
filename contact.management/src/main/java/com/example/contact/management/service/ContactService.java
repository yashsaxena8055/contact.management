package com.example.contact.management.service;

import com.example.contact.management.constant.enums.ContactStatus;
import com.example.contact.management.constant.enums.ResponseStatus;
import com.example.contact.management.dao.ContactCriteriaDAO;
import com.example.contact.management.dao.ContactDAO;
import com.example.contact.management.dao.UserDAO;
import com.example.contact.management.dto.*;
import com.example.contact.management.model.Contact;
import com.example.contact.management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    private ContactCriteriaDAO contactCriteriaDAO;
    private static final String CONTACT_CODE = "CONT";

    public ResponseObj create(CreateContactRequest request) {
        ResponseObj responseObj = new ResponseObj();
        User user = null;
        try {
            user = validateCreateRequest(request);
        } catch (Exception e) {
            return CommonUtilityService.setResponseObjError(e.getMessage(), responseObj, ResponseStatus.ERROR);
        }
        Contact contact = createObj(user, request);
        contact = contactDAO.save(contact);
        ContactDTO response = new ContactDTO();
        response.setContactCode(contact.getContactCode());
        response.setEmail(contact.getEmail());
        response.setFirstName(contact.getFirstName());
        response.setLastName(contact.getLastName());
        response.setPhoneNumber(contact.getPhoneNumber());
        response.setUserId(request.getUserId());

        return getResponseObj(responseObj, response, "Contact created successfully");
    }

    public ResponseObj fetchContacts(FetchContactsRequest request) {
        ResponseObj responseObj = new ResponseObj();
        if (Objects.isNull(request.getUserId())) {
            return CommonUtilityService.setResponseObjError("Please enter userId", responseObj, ResponseStatus.ERROR);
        }
        Page<ContactDTO> page = contactCriteriaDAO.fetchContacts(request);
        List<ContactDTO> contactList = new ArrayList<>();
        if (page.hasContent()) {
            contactList = page.toList();
        }
        FetchContactsResponse response = new FetchContactsResponse();
        response.setContactList(contactList);
        return getResponseObj(responseObj, response, "Contacts fetched successfully");
    }

    public ResponseObj fetchByCode(Integer userId, String contactCode) {
        ResponseObj responseObj = new ResponseObj();
        Contact contact = null;
        try {
            contact = getContact(userId,contactCode);
        } catch (Exception e) {
            return CommonUtilityService.setResponseObjError(e.getMessage(), responseObj, ResponseStatus.ERROR);
        }
        ContactDTO contactDTO = ContactDTO.builder()
                .email(contact.getEmail()).contactCode(contact.getContactCode())
                .phoneNumber(contact.getPhoneNumber()).firstName(contact.getFirstName())
                .lastName(contact.getLastName()).userId(userId).contactStatus(contact.getStatus()).build();
        return getResponseObj(responseObj, contactDTO, "Contact fetched successfully");
    }

    public ResponseObj deleteContact(Integer userId, String contactCode) {
        ResponseObj responseObj = new ResponseObj();
        Contact contact = null;
        try {
            contact = getContact(userId,contactCode);
        } catch (Exception e) {
            return CommonUtilityService.setResponseObjError(e.getMessage(), responseObj, ResponseStatus.ERROR);
        }
        contact.setStatus(ContactStatus.DELETED);
        contactDAO.save(contact);
        return getResponseObj(responseObj, null, "Contact deleted successfully");
    }

    public ResponseObj updateContact(UpdateContactRequest request){
        ResponseObj responseObj = new ResponseObj();
        Contact contact = null;
        try {
            contact = getContact(request.getUserId(),request.getContactCode());
        } catch (Exception e) {
            return CommonUtilityService.setResponseObjError(e.getMessage(), responseObj, ResponseStatus.ERROR);
        }
        updateContactDetails(contact,request);
        contactDAO.save(contact);
        return getResponseObj(responseObj, null, "Contact details updated successfully");
    }

    private void updateContactDetails(Contact contact,UpdateContactRequest request){
        if(StringUtils.hasText(request.getPhoneNumber()) && !request.getPhoneNumber().equals(contact.getPhoneNumber())){
            contact.setPhoneNumber(request.getPhoneNumber());
        }
        if(StringUtils.hasText(request.getEmail()) && !request.getEmail().equals(contact.getEmail())){
           contact.setEmail(request.getEmail());
        }
        if(StringUtils.hasText(request.getFirstName()) && !request.getFirstName().equals(contact.getFirstName())){
           contact.setFirstName(request.getFirstName());
        }
        if(StringUtils.hasText(request.getLastName()) && !request.getLastName().equals(contact.getLastName())){
          contact.setLastName(request.getLastName());
        }
    }

    private Contact getContact(Integer userId, String contactCode) throws Exception{
        Optional<User> user = userDAO.findById(userId);
        if (!user.isPresent()) {
            throw new Exception("Invalid user id");
        }
        Optional<Contact> contact = contactDAO.fetchByUserId(userId, contactCode);
        if (!contact.isPresent()) {
            throw new Exception("Contact not found");
        }
        return contact.get();
    }
    private static ResponseObj getResponseObj(ResponseObj responseObj, Object response, String msg) {
        responseObj.setData(response);
        responseObj.setStatus(ResponseStatus.OK);
        responseObj.setDescription(msg);
        return responseObj;
    }

    private Contact createObj(User user, CreateContactRequest request) {
        Contact contact = new Contact();
        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setContactCode(CONTACT_CODE + "_" + contact.getFirstName());
        contact.setStatus(ContactStatus.ACTIVE);
        contact.setEmail(request.getEmail());
        contact.setUser(user);
        contact.setPhoneNumber(request.getPhoneNumber());
        contact.setCreatedBy(user.getId());
        contact.setCreated(LocalDateTime.now());
        return contact;
    }

    private User validateCreateRequest(CreateContactRequest request) throws Exception {
        if (!StringUtils.hasText(request.getFirstName())) {
            throw new Exception("Cannot create contact as first name not found");
        }
        if (!StringUtils.hasText(request.getEmail())) {
            throw new Exception("Cannot create contact as email not found");
        }
        if (!StringUtils.hasText(request.getPhoneNumber())) {
            throw new Exception("Cannot create contact as phone_number not found");
        }
        if (Objects.isNull(request.getUserId())) {
            throw new Exception("Cannot create contact as user id not found");
        }
        User user = userDAO.findById(request.getUserId()).orElseThrow(() -> new Exception("Invalid userId"));
        List<Integer> contacts = contactDAO.validateContact(request.getUserId(), request.getFirstName(), request.getLastName(), request.getEmail(), request.getPhoneNumber());
        if (!CollectionUtils.isEmpty(contacts)) {
            throw new Exception("Cannot create contact as contact already present in the system");
        }
        return user;
    }
}
