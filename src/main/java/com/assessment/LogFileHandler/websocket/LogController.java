package com.assessment.LogFileHandler.websocket;

import com.assessment.LogFileHandler.service.LogFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogFileReader logFileReader;

    @Autowired
    public LogController(LogFileReader logFileReader) {
        this.logFileReader = logFileReader;
    }

    @GetMapping("/last10")
    public List<String> getLast10Lines() {
        try {
            return logFileReader.readLast10Lines();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of("Error reading log file");
        }
    }
}
