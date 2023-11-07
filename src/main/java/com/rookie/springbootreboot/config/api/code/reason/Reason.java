package com.rookie.springbootreboot.config.api.code.reason;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class Reason {
    private boolean isSuccess;
    private String message;
    private String code;
    private HttpStatus httpStatus;
}
