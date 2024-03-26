package com.project.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("db_message")
public class Message implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("chat_id")
    private Long chatId;
    @TableField("send_user_id")
    private Long sendUserId;
    @TableField("receiver_user_id")
    private Long receiverUserId;
    private String content;
    @TableField("is_read")
    private Integer isRead;
    @TableField("create_time")
    private Date createTime;
    @TableField("del_flag")
    private Integer delFlag;
}
