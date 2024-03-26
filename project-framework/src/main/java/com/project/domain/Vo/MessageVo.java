package com.project.domain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageVo {
    private Long id;
    private Long chatId;
    private Long sendUserId;
    private Long receiverUserId;
    private String content;
    private Integer isRead;
    private Date createTime;
}
