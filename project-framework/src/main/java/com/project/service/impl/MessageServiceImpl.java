package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.ResponseResult;
import com.project.domain.Vo.MessageListVo;
import com.project.domain.Vo.PageVo;
import com.project.domain.entity.Message;
import com.project.domain.entity.User;
import com.project.mapper.MessageMapper;
import com.project.service.MessageService;
import com.project.service.UserService;
import com.project.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Resource
    private UserService userService;

    @Override
    public ResponseResult getMessageList(Long pageNum, Long pageSize, Long chatId) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getChatId, chatId).orderByDesc(Message::getCreateTime);
        Page<Message> page = page(new Page<>(pageNum, pageSize), queryWrapper);
        List<MessageListVo> messageListVos = getMessageListVo(page.getRecords());
        return ResponseResult.okResult(new PageVo(pageNum, pageSize, page.getTotal(), messageListVos));
    }

    private List<MessageListVo> getMessageListVo(List<Message> records) {
        ArrayList<MessageListVo> messageListVos = new ArrayList<>();
        for (Message record : records) {
            MessageListVo messageListVo = BeanCopyUtils.copyBean(record, MessageListVo.class);
            //发送者信息查询
            User sendUser = userService.getById(record.getSendUserId());
            messageListVo.setSendUserAvatar(sendUser.getAvatar());
            messageListVo.setSendUserNickName(sendUser.getNickName());

            //接收者信息查询
            User receiverUser = userService.getById(record.getReceiverUserId());
            messageListVo.setReceiverUserAvatar(receiverUser.getAvatar());
            messageListVo.setReceiverUserNickName(receiverUser.getNickName());
            messageListVos.add(messageListVo);
        }
        return messageListVos;
    }
}
