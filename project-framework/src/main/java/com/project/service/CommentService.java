package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.ResponseResult;
import com.project.domain.entity.Comment;

public interface CommentService extends IService<Comment> {
    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
