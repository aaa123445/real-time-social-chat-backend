package com.project.server;

import com.alibaba.fastjson.JSONObject;
import com.project.domain.Vo.MessageVo;
import com.project.domain.entity.Message;
import com.project.domain.entity.User;
import com.project.service.MessageService;
import com.project.service.UserService;
import com.project.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
@ServerEndpoint("/websocket/{userId}")
//此注解相当于设置访问URL
public class WebSocket {

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
    private static Map<String, Session> sessionPool = new HashMap<>();
    private static MessageService messageService;
    private static UserService userService;
    private Session session;

    @Resource
    public void setMessageService(MessageService messageService) {
        WebSocket.messageService = messageService;
    }

    @Resource
    public void setUserService(UserService userService) {
        WebSocket.userService = userService;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        this.session = session;
        webSockets.add(this);
        sessionPool.put(userId, session);
        JSONObject sendData = new JSONObject();
        sendData.put("msg", userId + "连接成功");
        sendData.put("title", "服务端转发");
        this.sendOneMessage(userId, sendData.toJSONString());
        log.info("【websocket消息】有新的连接，总数为:{},当前接入人id:@{}", webSockets.size(), userId);
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        log.info("【websocket消息】连接断开，总数为:" + webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        MessageVo messageVo = JSONObject.toJavaObject(JSONObject.parseObject(message), MessageVo.class);
        User byId = userService.getById(messageVo.getSendUserId());
        Message bean = BeanCopyUtils.copyBean(messageVo, Message.class);
        bean.setCreateTime(new Date());
        messageService.save(bean);
        JSONObject sendData = new JSONObject();
        sendData.put("data", JSONObject.toJSONString(messageVo));
        sendData.put("msg", "来自@" + byId.getNickName());
        sendData.put("title", "收到一条私信");
        this.sendOneMessage(messageVo.getReceiverUserId().toString(), sendData.toJSONString());
        log.info("【websocket消息】收到客户端消息:" + message);
    }

    // 此为广播消息
    public void sendAllMessage(String message) {
        for (WebSocket webSocket : webSockets) {
            System.out.println("【websocket消息】广播消息:" + message);
            try {
                webSocket.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String userName, String message) {
        log.info("【websocket消息】单点消息:" + message);
        Session session = sessionPool.get(userName);
        if (session != null) {
            try {
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}