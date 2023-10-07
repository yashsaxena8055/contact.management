package com.example.contact.management.service;

import com.example.contact.management.constant.enums.ResponseStatus;
import com.example.contact.management.constant.enums.UserStatus;
import com.example.contact.management.constant.enums.UserType;
import com.example.contact.management.dao.UserDAO;
import com.example.contact.management.dto.LoginRequest;
import com.example.contact.management.dto.LoginResponse;
import com.example.contact.management.dto.ResponseObj;
import com.example.contact.management.dto.SignUpRequest;
import com.example.contact.management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TokenService tokenService;

    public ResponseObj doSignUp(SignUpRequest request){
        ResponseObj responseObj = new ResponseObj();
        try {
            validateSignUpRequest(request);
        } catch (Exception e) {
           return CommonUtilityService.setResponseObjError(e.getMessage(), responseObj,ResponseStatus.EXCEPTION);
        }

        User user = createUserObj(request);
        userDAO.save(user);
        return CommonUtilityService.setResponseObjSuccess("User Created Succesfully.",responseObj);
    }

    public ResponseObj doLogin(LoginRequest request){
        ResponseObj responseObj = new ResponseObj();
        if(!StringUtils.hasText(request.getUsername())){
           return CommonUtilityService.setResponseObjSuccess("Please enter username",responseObj);
        }
        if(!StringUtils.hasText(request.getPassword())){
            return CommonUtilityService.setResponseObjSuccess("Please enter password",responseObj);
        }
        User user = null;
        try {
             user = ValidateUser(request);
        } catch (Exception e) {
            return CommonUtilityService.setResponseObjError(e.getMessage(),responseObj,ResponseStatus.ERROR);
        }
       String token = tokenService.getJwtToken(user);
        LoginResponse loginResponse = LoginResponse.builder()
                .id(user.getId()).name(user.getName()).email(user.getEmail()).contact(user.getContact()).token(token).build();
       responseObj.setDescription("User loggedin successfully");
       responseObj.setData(loginResponse);
       responseObj.setStatus(ResponseStatus.OK);
       return responseObj;
    }


    private User ValidateUser(LoginRequest request)throws Exception{
        Optional<User> user = userDAO.findByUsername(request.getUsername());
        if(!user.isPresent()){
            throw new Exception("Incorrect username");
        }
        if(user.get().getStatus() == UserStatus.INACTIVE){
            throw new Exception("User is INACTIVE please contact to administrator");
        }
        if(user.get().getStatus() == UserStatus.DELETED){
            throw new Exception("User is deleted from our system");
        }
        if(!user.get().getPassword().equals(request.getPassword())){
            throw new Exception("Incorrect password");
        }
        return user.get();
    }
    private User createUserObj(SignUpRequest request){
        User user = new User();
        user.setContact(request.getContact());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.USER);
        user.setCreated(LocalDateTime.now());
        user.setCreatedBy(1);
        return user;
    }
    private void validateSignUpRequest(SignUpRequest request)throws Exception{
        if(!StringUtils.hasText(request.getName())){
            throw new Exception("name cannot be empty");
        }
        if(!StringUtils.hasText(request.getUsername())){
            throw new Exception("username cannot be empty");
        }
        Optional<User> opt = userDAO.findByUsername(request.getUsername());
        if (opt.isPresent()) {
            throw new Exception("User is already present in our System");
        }
        if(!CommonUtilityService.isDigit(request.getUsername())){
            throw new Exception("username should be your phone number");
        }
        if(!StringUtils.hasText(request.getPassword())){
            throw new Exception("password cannot be empty");
        }
    }
}
