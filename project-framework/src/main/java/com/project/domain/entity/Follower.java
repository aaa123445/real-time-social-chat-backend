package com.project.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("db_follower")
public class Follower {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("be_account_id")
    private Long beAccountId;
    @TableField("account_id")
    private Long AccountId;
    @TableField("del_flag")
    private Long delFlag;
}
