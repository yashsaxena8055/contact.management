package com.example.contact.management.dao;

import com.example.contact.management.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface ContactDAO extends JpaRepository<Contact,Integer> {

    @Query("select c.id from Contact c join c.user u where u.id =:userId and c.firstName =:firstName and c.lastName =:lastName and c.email =:email and c.phoneNumber =:phoneNumber")
    public List<Integer> validateContact(@RequestParam("userId") Integer userId ,@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName ,@RequestParam("email") String email
    ,@RequestParam("phoneNumber") String phoneNumber);

    @Query("select c from Contact c join c.user u where u.id=:userId and c.contactCode =:contactCode")
    Optional<Contact> fetchByUserId(@RequestParam("userId") Integer userId, @RequestParam("contactCode") String contactCode);
}
