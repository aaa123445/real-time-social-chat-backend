package com.project.domain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageListVo {
    private Long id;
    private Long chatId;
    //发送者信息
    private Long sendUserId;
    private String sendUserNickName;
    private String sendUserAvatar;
    // 接收者信息
    private Long receiverUserId;
    private String receiverUserNickName;
    private String receiverUserAvatar;
    private String content;
    private Integer isRead;
    private Date createTime;
}
