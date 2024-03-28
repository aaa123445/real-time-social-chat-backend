package com.project.service;

import com.project.domain.ResponseResult;
import com.project.domain.entity.User;

public interface AdminUserService {
    ResponseResult Adminlogin(User user);

    ResponseResult logout();
}
