package com.assessment.LogFileHandler.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Controller
public class LogWebSocketHandler extends TextWebSocketHandler {
    private final WebSocketServer webSocketServer;

    public LogWebSocketHandler(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketServer.registerSession(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketServer.unregisterSession(session);
        super.afterConnectionClosed(session, status);
    }
}
