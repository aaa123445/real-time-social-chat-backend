package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.ResponseResult;
import com.project.domain.Vo.UserVo;
import com.project.domain.entity.User;
import com.project.mapper.UserMapper;
import com.project.service.UserService;
import com.project.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public ResponseResult addUser() {
        getById(1);
        return null;
    }

    @Override
    public ResponseResult getUserInfo(Long id) {
        User byId = getById(id);
        UserVo userInfoVo = BeanCopyUtils.copyBean(byId, UserVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(UserVo userInfoVo) {
        update(BeanCopyUtils.copyBean(userInfoVo, User.class), new LambdaQueryWrapper<User>().eq(User::getId, userInfoVo.getId()));
        return ResponseResult.okResult();
    }
}
