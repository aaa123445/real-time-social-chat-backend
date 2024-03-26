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
@TableName("db_chat")
public class Chat implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("user1_id")
    private Long user1Id;
    @TableField("user2_id")
    private Long user2Id;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    @TableField("del_flag")
    private Integer delFlag;
}
