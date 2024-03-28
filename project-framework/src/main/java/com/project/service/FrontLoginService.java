package com.project.service;

import com.project.domain.ResponseResult;
import com.project.domain.entity.User;

public interface FrontLoginService {

    ResponseResult login(User user);

    ResponseResult logout();


}
