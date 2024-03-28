package com.project.controller;

import com.project.domain.ResponseResult;
import com.project.domain.Vo.AddArticleVo;
import com.project.service.ArticleService;
import com.project.utils.TencentCOSUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author jc
 * @since 2024-03-14
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @GetMapping("getList/{pageNum}/{pageSize}")
    public ResponseResult getArticleList(@PathVariable Long pageNum, @PathVariable Long pageSize) {
        return articleService.getArticleList(pageNum, pageSize, 0);
    }

    @PostMapping("addImage")
    public ResponseResult addImage(@RequestPart("file") MultipartFile file) {
        String url;
        try {
            url = TencentCOSUtil.upLoadImages(file);
        } catch (Exception e) {
            return ResponseResult.errorResult(505, e.getMessage());
        }
        return ResponseResult.okResult(url);
    }

    @PostMapping("addArticle")
    public ResponseResult addArticle(@RequestBody AddArticleVo article) {
        return articleService.addArticle(article);
    }

    @PostMapping("delArticle/{id}")
    public ResponseResult delArticle(@PathVariable Long id) {
        return articleService.delArticle(id);
    }

    @GetMapping("getSelfList/{pageNum}/{pageSize}/{uId}")
    public ResponseResult getSelfArticleList(@PathVariable Long pageNum, @PathVariable Long pageSize, @PathVariable Long uId) {
        return articleService.getSelfArticleList(pageNum, pageSize, uId);
    }
}
