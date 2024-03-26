package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.ResponseResult;
import com.project.domain.Vo.AddArticleVo;
import com.project.domain.entity.Article;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author jc
 * @since 2024-03-14
 */
public interface ArticleService extends IService<Article> {

    ResponseResult getArticleList(Long pageNum, Long pageSize);

    ResponseResult addArticle(AddArticleVo article);

    ResponseResult delArticle(Long id);

    ResponseResult getSelfArticleList(Long pageNum, Long pageSize, Long uId);
}
