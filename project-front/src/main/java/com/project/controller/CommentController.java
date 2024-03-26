package com.project.controller;

import com.project.domain.ResponseResult;
import com.project.service.CommentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @GetMapping("getList/{articleId}/{pageNum}/{pageSize}")
    public ResponseResult getList(@PathVariable Long articleId, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        return commentService.commentList(articleId, pageNum, pageSize);
    }
}
