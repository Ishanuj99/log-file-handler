package com.assessment.LogFileHandler.config;

import com.assessment.LogFileHandler.service.FileUpdater;
import com.assessment.LogFileHandler.service.LogFileReader;
import com.assessment.LogFileHandler.websocket.WebSocketServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig{

    @Bean
    public LogFileReader logFileReader(){
        return new LogFileReader("/Users/ishanuj.hazarika/Downloads/sample-logfile.log");
    }

    @Bean
    public WebSocketServer webSocketServer(){
        return new WebSocketServer();
    }

    @Bean
    public Thread fileUpdaterThread(LogFileReader logFileReader, WebSocketServer webSocketServer){
        FileUpdater fileUpdater = new FileUpdater(logFileReader, webSocketServer);
        Thread thread = new Thread(fileUpdater);
        thread.start();
        return thread;
    }
}