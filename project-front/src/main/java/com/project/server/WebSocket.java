package com.project.server;

import com.alibaba.fastjson.JSONObject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
    private Session session;

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
        JSONObject sendData = new JSONObject();
        sendData.put("msg", message);
        sendData.put("title", "服务端转发");
        this.sendOneMessage("14787164048663", sendData.toJSONString());
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