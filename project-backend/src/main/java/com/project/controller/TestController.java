package com.project.controller;

import com.project.domain.ResponseResult;
import com.project.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private ArticleService articleService;

    @GetMapping("/{pageNum}/{pageSize}")
    public ResponseResult test(@PathVariable Long pageNum, @PathVariable Long pageSize) {
        return articleService.getArticleList(pageNum, pageSize, 0);
    }
}
