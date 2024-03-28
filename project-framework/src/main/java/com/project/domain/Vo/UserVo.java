package com.project.domain.Vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserVo {
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 账号状态（0正常 1停用）
     */
    private String status;
    /**
     * 邮箱
     */
    private String email;

    private String signature;
    /**
     * 手机号
     */
    private String phonenumber;
    /**
     * 用户性别（0男，1女，2未知）
     */
    private String sex;
    /**
     * 头像
     */
    private String avatar;


}
