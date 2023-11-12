package com.rookie.springbootreboot.config.api;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rookie.springbootreboot.config.api.code.BaseCode;
import com.rookie.springbootreboot.config.api.code.status.SuccessStatus;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "data"})
@ToString
public class BaseResponse<T> {

    private Boolean isSuccess;
    private String code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 일반적인 요청 성공시
     * */
    public static <T> BaseResponse<T> onSuccess(T result) {
        return new BaseResponse<>(true, SuccessStatus.OK.getCode(), SuccessStatus.OK.getMessage(), result);
    }

    /**
     * 리소스 생성 요청 성공시 (POST, PUT)
     * */
    public static <T> BaseResponse<T> onCreate(T result) {
        return new BaseResponse<>(true, SuccessStatus.CREATED.getCode(), SuccessStatus.CREATED.getMessage(), result);
    }

    public static <T> BaseResponse<T> of(BaseCode code, T result) {
        return new BaseResponse<>(true,code. getReasonHttpStatus().getCode(), code.getReasonHttpStatus().getMessage(), result);
    }

    /**
     * 예외 처리시
     * */
    public static <T> BaseResponse<T> onFailure(String code, String message, @Nullable T data) {
        return new BaseResponse<>(false, code, message, data);
    }
}
