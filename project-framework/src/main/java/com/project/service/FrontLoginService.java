package com.project.service;

import com.project.domain.ResponseResult;
import com.project.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

public interface FrontLoginService {

    ResponseResult login(User user);

    ResponseResult logout();

}
