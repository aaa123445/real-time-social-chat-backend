package com.project.controller;

import com.project.domain.ResponseResult;
import com.project.domain.entity.User;
import com.project.enums.AppHttpCodeEnum;
import com.project.exception.SystemException;
import com.project.service.AdminUserService;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserLoginController {

    @Resource
    private AdminUserService adminUserService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminUserService.Adminlogin(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout() {
        return adminUserService.logout();
    }
}
