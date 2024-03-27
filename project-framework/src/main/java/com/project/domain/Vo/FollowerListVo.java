package com.project.domain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowerListVo {

    private Long id;
    private Long beAccountId;
    private String beAccountNickName;
    private String beAccountAvatar;


}
