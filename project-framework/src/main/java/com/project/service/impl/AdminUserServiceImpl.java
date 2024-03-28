package com.project.service.impl;

import com.project.domain.ResponseResult;
import com.project.domain.Vo.FrontUserLoginVo;
import com.project.domain.Vo.UserInfoVo;
import com.project.domain.entity.LoginUser;
import com.project.domain.entity.User;
import com.project.service.AdminUserService;
import com.project.utils.BeanCopyUtils;
import com.project.utils.JwtUtil;
import com.project.utils.RedisCache;
import com.project.utils.SecurityUtils;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;


    @Override
    public ResponseResult Adminlogin(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        if (!"1".equals(loginUser.getUser().getType()))
            throw new RuntimeException("无权限访问");
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("admin-login:" + userId, loginUser);
        //设置token的过期时间
        redisCache.expire("admin-login:" + userId, 24 * 60 * 60 * 1000L);

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        FrontUserLoginVo vo = new FrontUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //获取userid
        Long userId = SecurityUtils.getUserId();
        //删除redis中的用户信息
        redisCache.deleteObject("admin-login:" + userId);
        return ResponseResult.okResult();
    }
}
