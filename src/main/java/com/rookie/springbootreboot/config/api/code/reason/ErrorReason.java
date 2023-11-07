package com.rookie.springbootreboot.config.api.code.reason;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@Builder
public class ErrorReason {
    private boolean isSuccess;
    private String message;
    private String code;
    private HttpStatusCode httpStatus;
}
