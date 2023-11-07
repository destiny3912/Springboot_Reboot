package com.rookie.springbootreboot.config.api.code;

import com.rookie.springbootreboot.config.api.code.reason.ErrorReason;

public interface BaseErrorCode {
    public ErrorReason getReason();
    public ErrorReason getReasonHttpStatus();
}
