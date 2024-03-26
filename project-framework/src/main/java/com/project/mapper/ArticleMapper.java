package com.project.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author jc
 * @since 2024-03-14
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
