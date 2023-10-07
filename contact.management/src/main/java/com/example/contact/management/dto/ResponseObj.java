package com.example.contact.management.dto;

import com.example.contact.management.constant.enums.ResponseStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObj {
    private ResponseStatus status;
    private String description;
    private Object data;
    private String errorMessage;

}
