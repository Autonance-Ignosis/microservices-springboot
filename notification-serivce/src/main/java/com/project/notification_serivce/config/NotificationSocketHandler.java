package com.project.notification_serivce.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NotificationSocketHandler extends TextWebSocketHandler {

    private final Map<Long, List<WebSocketSession>> userSessions = new ConcurrentHashMap<>();

    public void sendNotification(Long userId, String message) throws IOException {
        if (userSessions.containsKey(userId)) {
            for (WebSocketSession session : userSessions.get(userId)) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = extractUserId(session); // from headers or query param
        userSessions.computeIfAbsent(userId, k -> new ArrayList<>()).add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        userSessions.values().forEach(list -> list.remove(session));
    }

    private Long extractUserId(WebSocketSession session) {
        // e.g., from query param `?userId=123`
        String query = session.getUri().getQuery();
        return Long.parseLong(query.split("=")[1]);
    }
}
