package com.rookie.springbootreboot.config.api.code;

import com.rookie.springbootreboot.config.api.code.reason.Reason;

public interface BaseCode {
    public Reason getReason();
    public Reason getReasonHttpStatus();
}
