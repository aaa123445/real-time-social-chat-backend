package com.project.domain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrontUserLoginVo {

    private String token;
    private UserInfoVo userInfo;
}