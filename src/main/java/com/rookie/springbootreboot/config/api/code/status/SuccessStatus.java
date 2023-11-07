package com.rookie.springbootreboot.config.api.code.status;

import com.rookie.springbootreboot.config.api.code.BaseCode;
import com.rookie.springbootreboot.config.api.code.reason.Reason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    // 일반적인 응답
    OK(HttpStatus.OK, "COMMON200", "요청 성공"),
    CREATED(HttpStatus.CREATED, "COMMON201", "생성 요청 성공");


    /**
     * 하부에 성공과 관련된 응답이 더 필요하다면 사용
     * 굳이 사용하지 않아도 상관 없으나 세분화가 필요하다면 사용
     * */

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public Reason getReason() {
        return Reason.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public Reason getReasonHttpStatus() {
        return Reason.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}
