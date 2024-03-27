package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.ResponseResult;
import com.project.domain.Vo.AddChatVo;
import com.project.domain.Vo.ChatListVo;
import com.project.domain.Vo.PageVo;
import com.project.domain.entity.Chat;
import com.project.domain.entity.Message;
import com.project.domain.entity.User;
import com.project.mapper.ChatMapper;
import com.project.service.ChatService;
import com.project.service.MessageService;
import com.project.service.UserService;
import com.project.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {

    @Resource
    private UserService userService;
    @Resource
    private MessageService messageService;

    @Override
    public ResponseResult addChat(AddChatVo addChatVo) {
        Chat chat = BeanCopyUtils.copyBean(addChatVo, Chat.class);
        LambdaQueryWrapper<Chat> chatLambdaQueryWrapper = new LambdaQueryWrapper<>();
        chatLambdaQueryWrapper.eq(Chat::getUser1Id, addChatVo.getUser1Id()).eq(Chat::getUser2Id, addChatVo.getUser2Id()).or()
                .eq(Chat::getUser1Id, addChatVo.getUser2Id()).eq(Chat::getUser2Id, addChatVo.getUser1Id());
        List<Chat> chatList = list(chatLambdaQueryWrapper);
        if (!chatList.isEmpty()) {
            for (Chat obj : chatList) {
                obj.setUpdateTime(new Date());
                update(obj, chatLambdaQueryWrapper);
            }
            return ResponseResult.okResult();
        } else {
            chat.setCreateTime(new Date());
            chat.setUpdateTime(new Date());
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
        queryWrapper.eq(Chat::getUser1Id, userId).or().eq(Chat::getUser2Id, userId).orderByDesc(Chat::getUpdateTime);
        Page<Chat> page = page(new Page<>(pageNum, pageSize), queryWrapper);
        List<ChatListVo> chatListVo = getChatListVo(page.getRecords(), userId);
        return ResponseResult.okResult(new PageVo(pageNum, pageSize, page.getTotal(), chatListVo));
    }

    private List<ChatListVo> getChatListVo(List<Chat> records, Long userId) {
        List<ChatListVo> chatListVos = new ArrayList<>();
        for (Chat record : records) {
            ChatListVo chatListVo = new ChatListVo();
            chatListVo.setId(record.getId());
            chatListVo.setTime(record.getUpdateTime());
            if (record.getUser1Id().equals(userId)) {
                User byId = userService.getById(record.getUser2Id());
                chatListVo.setNickName(byId.getNickName());
                chatListVo.setAvatar(byId.getAvatar());
                chatListVo.setReceiverId(record.getUser2Id());
            } else {
                User byId = userService.getById(record.getUser1Id());
                chatListVo.setNickName(byId.getNickName());
                chatListVo.setAvatar(byId.getAvatar());
                chatListVo.setReceiverId(record.getUser1Id());
            }
            LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(Message::getSendUserId, record.getUser1Id()).eq(Message::getReceiverUserId, record.getUser2Id())
                    .or()
                    .eq(Message::getSendUserId, record.getUser2Id()).eq(Message::getReceiverUserId, record.getUser1Id())
                    .eq(Message::getChatId, record.getId())
                    .orderByDesc(Message::getCreateTime);
            List<Message> list = messageService.list(queryWrapper);
            if (!list.isEmpty()) {
                chatListVo.setLastMessage(list.get(0).getContent());
                chatListVo.setTime(list.get(0).getCreateTime());
            } else {
                chatListVo.setLastMessage("");
            }
            chatListVos.add(chatListVo);
        }
        return chatListVos;
    }
}
