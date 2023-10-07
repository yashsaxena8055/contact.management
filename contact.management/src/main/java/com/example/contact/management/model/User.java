package com.example.contact.management.model;

import com.example.contact.management.constant.enums.UserStatus;
import com.example.contact.management.constant.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;
    @Column(name = "contact")
    private String contact;
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(name = "created")
    private LocalDateTime created;
    @Column(name = "created_by")
    private Integer createdBy;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Contact> contacts;
}
