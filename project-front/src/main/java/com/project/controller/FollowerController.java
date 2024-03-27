package com.project.controller;

import com.project.domain.ResponseResult;
import com.project.domain.Vo.AddFollowerVo;
import com.project.service.FollowerService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/follower")
public class FollowerController {
    @Resource
    private FollowerService followerService;

    @GetMapping("getFollowerList/{id}")
    public ResponseResult getFollowerList(@PathVariable Long id) {
        return followerService.getFollowerList(id);
    }

    @PostMapping("addFollower")
    public ResponseResult addFollower(@RequestBody AddFollowerVo addFollowerVo) {
        return followerService.addFollower(addFollowerVo);
    }

    @PostMapping("delFollower")
    public ResponseResult delFollower(@RequestBody AddFollowerVo addFollowerVo) {
        return followerService.delFollower(addFollowerVo);
    }

    @GetMapping("getFollower/{id}")
    public ResponseResult getList(@PathVariable Long id) {
        return followerService.getList(id);
    }
}
