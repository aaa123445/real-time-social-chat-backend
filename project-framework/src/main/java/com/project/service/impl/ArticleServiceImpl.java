package com.project.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.ResponseResult;
import com.project.domain.Vo.*;
import com.project.domain.entity.Article;
import com.project.domain.entity.User;
import com.project.enums.AppHttpCodeEnum;
import com.project.mapper.ArticleMapper;
import com.project.mapper.UserMapper;
import com.project.service.ArticleService;
import com.project.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author jc
 * @since 2024-03-14
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private UserMapper userMapper;


    /*
     * 获取文章列表（首页）
     * @param pageNum 分页页码
     * @param pageSize 分页大小
     * @return 文章列表
     */
    @Override
    public ResponseResult getArticleList(Long pageNum, Long pageSize, Integer status) {
        //先查询对应页码和每页大小的文章数据
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getStatus, 0)
                .orderByDesc(Article::getCreateTime);
        Page<Article> articlePage = page(new Page<>(pageNum, pageSize), articleLambdaQueryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleListVo> articleListVo = getArticleListVo(records);
        return ResponseResult.okResult(new PageVo(pageNum, pageSize, articlePage.getTotal(), articleListVo));
    }

    /*
     * 获取文章列表（个人主页）
     */
    @Override
    public ResponseResult getSelfArticleList(Long pageNum, Long pageSize, Long uId) {
        //先查询对应页码和每页大小的文章数据
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getStatus, 0)
                .eq(Article::getCreateBy, uId)
                .orderByDesc(Article::getCreateTime);
        Page<Article> articlePage = page(new Page<>(pageNum, pageSize), articleLambdaQueryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleListVo> articleListVo = getArticleListVo(records);
        return ResponseResult.okResult(new PageVo(pageNum, pageSize, articlePage.getTotal(), articleListVo));
    }

    /*
     * 发布文章
     */
    @Override
    public ResponseResult addArticle(AddArticleVo articleVo) {
        Article article = BeanCopyUtils.copyBean(articleVo, Article.class);
        article.setCreateTime(new Date());
        save(article);
        return ResponseResult.okResult();
    }

    /*
     * 删除文章
     */
    @Override
    public ResponseResult delArticle(Long id) {
        if (removeById(id)) return ResponseResult.okResult();
        else return ResponseResult.errorResult(AppHttpCodeEnum.DEL_ARTICLE_FAILED);
    }

    /*
     * 获取文章图片和用户头像
     */
    private List<ArticleListVo> getArticleListVo(List<Article> records) {
        List<ArticleListVo> res = new ArrayList<>();
        for (Article record : records) {
            ArticleListVo vo = BeanCopyUtils.copyBean(record, ArticleListVo.class);
            //获取头像
            Long createBy = record.getCreateBy();
            User user = userMapper.selectById(createBy);
            UserAvatarVo userAvatarVo = BeanCopyUtils.copyBean(user, UserAvatarVo.class);
            vo.setUser(userAvatarVo);
            //获取图片
            String images = record.getImages();
            List<ImagesVo> imagesList = new ArrayList<>();
            if (!StringUtils.isEmpty(images)) {
                String[] split = images.split(",");
                for (String s : split) {
                    ImagesVo imagesVo = new ImagesVo();
                    imagesVo.setImageUrl(s);
                    imagesVo.setId((long) Math.abs(UUID.randomUUID().hashCode()));
                    imagesList.add(imagesVo);
                }
            }
            vo.setImages(imagesList);
            res.add(vo);
        }
        return res;
    }

}
