package com.project.controller;

import com.project.domain.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("test")
    public ResponseResult test(){
        return ResponseResult.okResult();
    }
}
