package com.project.domain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {
    private Long pageNum;//当前页
    private Long pageSize;//每页显示条数
    private Long total;//总条数
    private List<?> resData;//数据集合
}
