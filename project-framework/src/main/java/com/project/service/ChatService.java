package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.ResponseResult;
import com.project.domain.Vo.AddChatVo;
import com.project.domain.entity.Chat;

public interface ChatService extends IService<Chat> {
    ResponseResult addChat(AddChatVo addChatVo);

    ResponseResult getChatList(Long pageNum, Long pageSize, Long userId);
}
