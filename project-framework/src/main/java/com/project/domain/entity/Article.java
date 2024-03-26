package com.project.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author jc
 * @since 2024-03-14
 */
@Getter
@Setter
@TableName("db_article")
public class Article implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章内容
     */
    @TableField("content")
    private String content;


    /**
     * 所属分类id
     */
    @TableField("category_id")
    private Long categoryId;
    /*
     * 图片id
     */
    @TableField("images")
    private String images;
    /**
     * 访问量
     */
    @TableField("start_count")
    private Long startCount;

    @TableField("forward_count")
    private Long forwardCount;

    @TableField("comment_count")
    private Long commentCount;

    @TableField("create_by")
    private Long createBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("status")
    private Integer status;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableField("del_flag")
    private Integer delFlag;
}
