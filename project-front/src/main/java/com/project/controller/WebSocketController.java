package com.project.controller;

import com.project.domain.ResponseResult;
import com.project.domain.Vo.AddChatVo;
import com.project.server.WebSocket;
import com.project.service.ChatService;
import com.project.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/websocket")
public class WebSocketController {

    @Resource
    private WebSocket webSocket;

    @Resource
    private ChatService chatService;
    @Resource
    private MessageService messageService;

    @GetMapping("getChatList/{pageNum}/{pageSize}/{userId}")
    public ResponseResult getChatList(@PathVariable Long pageNum, @PathVariable Long pageSize, @PathVariable Long userId) {
        return chatService.getChatList(pageNum, pageSize, userId);
    }

    @PostMapping("addChat")
    public ResponseResult addChat(@RequestBody AddChatVo addChatVo) {
        return chatService.addChat(addChatVo);
    }

    @GetMapping("/sendAllWebSocket")
    public String test() {
        String text = "你们好！这是websocket群体发送！";
        webSocket.sendAllMessage(text);
        return text;
    }

    @GetMapping("/sendOneWebSocket/{userId}")
    public String sendOneWebSocket(@PathVariable("userId") String userId) {
        String text = userId + " 你好！ 这是websocket单人发送！";
        webSocket.sendOneMessage(userId, text);
        return text;
    }
}
