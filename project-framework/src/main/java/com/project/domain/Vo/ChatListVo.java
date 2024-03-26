package com.project.domain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatListVo {
    private Long id;
    private String avatar;
    private String nickName;
    private Long receiverId;
    private Date time;
    private String lastMessage;
}
