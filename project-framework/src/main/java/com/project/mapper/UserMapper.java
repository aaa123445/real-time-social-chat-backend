package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
