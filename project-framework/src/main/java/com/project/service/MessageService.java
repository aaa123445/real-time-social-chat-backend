package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.ResponseResult;
import com.project.domain.entity.Message;

public interface MessageService extends IService<Message> {
    ResponseResult getMessageList(Long pageNum, Long pageSize, Long chatId);
}
