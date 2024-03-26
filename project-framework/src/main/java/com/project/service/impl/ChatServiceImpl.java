package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.ResponseResult;
import com.project.domain.Vo.AddChatVo;
import com.project.domain.entity.Chat;
import com.project.mapper.ChatMapper;
import com.project.service.ChatService;
import com.project.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {
    @Override
    public ResponseResult addChat(AddChatVo addChatVo) {
        Chat chat = BeanCopyUtils.copyBean(addChatVo, Chat.class);
        chat.setCreateTime(new Date());
        LambdaQueryWrapper<Chat> chatLambdaQueryWrapper = new LambdaQueryWrapper<>();
        chatLambdaQueryWrapper.eq(Chat::getUser1Id, addChatVo.getUser1Id()).eq(Chat::getUser2Id, addChatVo.getUser2Id()).or()
                .eq(Chat::getUser1Id, addChatVo.getUser2Id()).eq(Chat::getUser2Id, addChatVo.getUser1Id());
        List<Chat> chatList = list(chatLambdaQueryWrapper);
        if (!chatList.isEmpty()) {
            for (Chat obj : chatList) {
                obj.setCreateTime(new Date());
                update(obj, chatLambdaQueryWrapper);
            }
            return ResponseResult.okResult();
        } else {
            if (save(chat)) {
                return ResponseResult.okResult();
            } else {
                return ResponseResult.errorResult(505, "错误");
            }
        }
    }

    @Override
    public ResponseResult getChatList(Long pageNum, Long pageSize, Long userId) {
        LambdaQueryWrapper<Chat> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Chat::getUser1Id, userId).or().eq(Chat::getUser2Id, userId);
        Page<Chat> page = page(new Page<>(pageNum, pageSize), queryWrapper);

        return ResponseResult.okResult(page.getRecords());
    }
}
