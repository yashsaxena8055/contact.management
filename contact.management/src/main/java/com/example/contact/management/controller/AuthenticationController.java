package com.example.contact.management.controller;

import com.example.contact.management.constant.ApiUriConstant;
import com.example.contact.management.dto.LoginRequest;
import com.example.contact.management.dto.ResponseObj;
import com.example.contact.management.dto.SignUpRequest;
import com.example.contact.management.service.AuthenticationService;
import com.example.contact.management.service.CommonUtilityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUriConstant.AUTH_BASE_URI)
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(ApiUriConstant.DO_SIGN_UP)
    @ApiOperation(value = "Creating user in database",response = ResponseObj.class)
    public ResponseEntity<ResponseObj> doSignUp(@RequestBody SignUpRequest request){
        ResponseObj response = authenticationService.doSignUp(request);
        return CommonUtilityService.setResponseEntity(response);
    }

    @PostMapping(ApiUriConstant.DO_LOGIN)
    @ApiOperation(value = "Do login user and get jwt token",response = ResponseObj.class)
    public ResponseEntity<ResponseObj> doLogin(@RequestBody LoginRequest request){
        ResponseObj response = authenticationService.doLogin(request);
        return CommonUtilityService.setResponseEntity(response);
    }

}
