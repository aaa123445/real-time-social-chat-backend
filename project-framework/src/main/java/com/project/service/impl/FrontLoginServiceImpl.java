package com.project.service.impl;

import com.project.domain.ResponseResult;
import com.project.domain.Vo.FrontUserLoginVo;
import com.project.domain.Vo.UserInfoVo;
import com.project.domain.entity.LoginUser;
import com.project.domain.entity.User;
import com.project.service.FrontLoginService;
import com.project.utils.BeanCopyUtils;
import com.project.utils.JwtUtil;
import com.project.utils.RedisCache;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FrontLoginServiceImpl implements FrontLoginService {
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);
        //设置token的过期时间
        redisCache.expire("login:"+userId,24*60 * 60 *1000L);

        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        FrontUserLoginVo vo = new FrontUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //获取token 解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }
}
