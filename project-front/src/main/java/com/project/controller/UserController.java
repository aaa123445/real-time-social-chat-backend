package com.project.controller;

import com.project.domain.ResponseResult;
import com.project.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("getUserInfo/{id}")
    private ResponseResult getUserInfo(@PathVariable Long id) {
        return userService.getUserInfo(id);
    }
}
