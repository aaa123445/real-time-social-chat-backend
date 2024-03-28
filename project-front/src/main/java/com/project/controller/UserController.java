package com.project.controller;

import com.project.domain.ResponseResult;
import com.project.domain.Vo.UserVo;
import com.project.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("getUserInfo/{id}")
    public ResponseResult getUserInfo(@PathVariable Long id) {
        return userService.getUserInfo(id);
    }

    @PostMapping("updateUserInfo")
    public ResponseResult updateUserInfo(@RequestBody UserVo userVo) {
        return userService.updateUserInfo(userVo);
    }
}
