package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.ResponseResult;
import com.project.domain.Vo.AddFollowerVo;
import com.project.domain.entity.Follower;
import com.project.mapper.FollowerMapper;
import com.project.service.FollowerService;
import com.project.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowerServiceImpl extends ServiceImpl<FollowerMapper, Follower> implements FollowerService {
    @Override
    public ResponseResult getFollowerList(Long id) {
        LambdaQueryWrapper<Follower> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follower::getAccountId, id);
        List<Follower> list = list(queryWrapper);
        List<Long> collect = list.stream().map(Follower::getBeAccountId).collect(Collectors.toList());
        return ResponseResult.okResult(collect);
    }

    @Override
    public ResponseResult addFollower(AddFollowerVo addFollowerVo) {
        Follower follower = BeanCopyUtils.copyBean(addFollowerVo, Follower.class);
        if (save(follower)) {
            return ResponseResult.okResult();
        } else {
            return ResponseResult.errorResult(506, "关注失败");
        }
    }

    @Override
    public ResponseResult delFollower(AddFollowerVo addFollowerVo) {
        LambdaQueryWrapper<Follower> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follower::getAccountId, addFollowerVo.getAccountId())
                .eq(Follower::getBeAccountId, addFollowerVo.getBeAccountId());
        if (remove(queryWrapper)) {
            return ResponseResult.okResult();
        } else {
            return ResponseResult.errorResult(505, "取消关注失败");
        }
    }
}
