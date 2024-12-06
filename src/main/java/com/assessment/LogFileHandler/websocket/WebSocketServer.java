package com.assessment.LogFileHandler.websocket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class WebSocketServer {
    private CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    public void registerSession(WebSocketSession webSocketSession){
        System.out.println("Registering session: " + webSocketSession.getId());
        sessions.add(webSocketSession);
    }
    public void unregisterSession(WebSocketSession webSocketSession){
        System.out.println("Unregistering session: " + webSocketSession.getId());
        sessions.remove(webSocketSession);
    }
    public void sendUpdate(String message){
        System.out.println("in send update, message: " + message);
        try{
            if(sessions.isEmpty()){
                System.out.println("No active sessions to send updates");
            }
            else {
                for(WebSocketSession session: sessions){
                    System.out.println("Sending message to session: " + session.getId());
                    session.sendMessage(new TextMessage(message));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}