package com.example.contact.management.constant;

public class ApiUriConstant {
    public ApiUriConstant(){};
    public static final String AUTH_BASE_URI = "/auth";
    public static final String DO_LOGIN = "/login";
    public static final String DO_SIGN_UP = "/sign_up";
    public static final String CONTACT_BASE_URI = "/contact";
    public static final String CREATE_CONTACT = "/create";
    public static final String FETCH_CONTACTS = "/fetchAll";
    public static final String FETCH_CONATCT_BY_CODE = "/fetchByCode/{userId}/{contactCode}";
    public static final String DELETE_CONTACT_BY_CODE = "/delete/{userId}/{contactCode}";
    public static final String UPDATE_CONTACT_BY_CODE = "/update";

}
