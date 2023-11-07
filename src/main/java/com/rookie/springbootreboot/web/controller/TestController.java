package com.rookie.springbootreboot.web.controller;

import com.rookie.springbootreboot.config.api.BaseException;
import com.rookie.springbootreboot.config.api.BaseResponse;
import com.rookie.springbootreboot.config.api.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("")
    public BaseResponse<String> test(){
        throw new BaseException(ErrorStatus.INTERNAL_SERVER_ERROR);

//        return BaseResponse.onSuccess("Success");
    }
}
