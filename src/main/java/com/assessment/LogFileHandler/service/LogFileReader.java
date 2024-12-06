package com.assessment.LogFileHandler.service;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class LogFileReader {
    private File logFile;
    private long logFilePointer;

    public LogFileReader(@Value("${logfile.file.path}") String filePath){
        this.logFile = new File(filePath);
        this.logFilePointer = 0;
    }

    public List<String> readLast10Lines() throws IOException {
        LinkedList<String> lines= new LinkedList<>();
        RandomAccessFile file = new RandomAccessFile(logFile, "r");
        int count = 0;
        long filePointer = file.length()-1;
        StringBuilder line = new StringBuilder();
        while(count < 10 && filePointer>=0){
            file.seek(filePointer);
            char c = (char) file.read();
            if(c == '\n' && line.length()>0){
                String lineToAdd = line.reverse().toString();
                lines.addFirst(lineToAdd);
                line.setLength(0);
                count++;
            }
            else if(c != '\n'){
                line.append(c);
            }
            filePointer--;
        }
        file.close();
        return lines;
    }
}
