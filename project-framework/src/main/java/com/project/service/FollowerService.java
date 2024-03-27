package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.ResponseResult;
import com.project.domain.Vo.AddFollowerVo;
import com.project.domain.entity.Follower;

public interface FollowerService extends IService<Follower> {
    ResponseResult getFollowerList(Long id);

    ResponseResult addFollower(AddFollowerVo addFollowerVo);

    ResponseResult delFollower(AddFollowerVo addFollowerVo);

    ResponseResult getList(Long id);
}
