package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.ResponseResult;
import com.project.domain.Vo.UserVo;
import com.project.domain.entity.User;

public interface UserService extends IService<User> {
    ResponseResult addUser();

    ResponseResult getUserInfo(Long id);

    ResponseResult updateUserInfo(UserVo userInfoVo);
}
