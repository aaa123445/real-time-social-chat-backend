package com.project.domain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListVo {

    private Long id;

    private UserAvatarVo user;

    private String content;

    private List<ImagesVo> images;

    private Long startCount;

    private Long commentCount;

    private boolean flag = false;

    private Long forwardCount;


    private Date createTime;

    private Date updateTime;
}
