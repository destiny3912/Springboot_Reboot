package com.rookie.springbootreboot.config.api;

import com.rookie.springbootreboot.config.api.code.BaseErrorCode;
import com.rookie.springbootreboot.config.api.code.reason.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BaseException은 사실 런타임에 발생하는 예외
 * 따라서 최상위 Exception이 아닌 RuntimeException을 상속 받음
 * */
@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException{
    private BaseErrorCode code;

    public ErrorReason getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReason getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}
