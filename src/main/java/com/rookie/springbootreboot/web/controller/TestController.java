package com.rookie.springbootreboot.web.controller;

import com.rookie.springbootreboot.config.api.BaseException;
import com.rookie.springbootreboot.config.api.BaseResponse;
import com.rookie.springbootreboot.config.api.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("")
    public BaseResponse<String> test(@RequestParam("test2") String test2){
//        throw MethodArgumentNotValidException;

        return BaseResponse.onSuccess("Success");
    }
}
