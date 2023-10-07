package com.example.contact.management.service;

import com.example.contact.management.constant.enums.ResponseStatus;
import com.example.contact.management.dto.ResponseObj;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class CommonUtilityService {

    public static boolean isDigit(String str) {
        if (!StringUtils.hasText(str)) return false;
        return str.matches("\\d+");
    }

    public static ResponseObj setResponseObjError(String message, ResponseObj responseObj, ResponseStatus status) {
        responseObj.setErrorMessage(message);
        responseObj.setStatus(status);
        if (status == ResponseStatus.EXCEPTION || status == ResponseStatus.BADREQUEST || status == ResponseStatus.UNAUTHERIZED)
            responseObj.setErrorMessage(message);
        else
            responseObj.setDescription(message);
        return responseObj;
    }

    public static ResponseObj setResponseObjSuccess(String message, ResponseObj responseObj) {
        responseObj.setDescription(message);
        responseObj.setStatus(ResponseStatus.OK);
        return responseObj;
    }

    public static ResponseEntity setResponseEntity(ResponseObj responseObj) {
        if(Objects.nonNull(responseObj.getStatus())){
            switch (responseObj.getStatus()) {
                case BADREQUEST:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObj);
                case OK:
                case EXCEPTION:
                case ERROR:
                    return ResponseEntity.ok(responseObj);
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ISE in setting response entity objecct");
    }

    public static Pageable getPageable(Integer size ,Integer pageNumber){
        if(Objects.isNull(size)){
            size = 10;
        }
        if(Objects.isNull(pageNumber)){
            pageNumber = 0;
        }
        return PageRequest.of(pageNumber,size);
    }
}
