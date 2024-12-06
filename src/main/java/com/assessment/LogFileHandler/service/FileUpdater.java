package com.assessment.LogFileHandler.service;

import com.assessment.LogFileHandler.websocket.WebSocketServer;
import lombok.AllArgsConstructor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

@AllArgsConstructor
public class FileUpdater implements Runnable {
    private LogFileReader logFileReader;
    private WebSocketServer webSocketServer;

    @Override
    public void run() {
        RandomAccessFile file = null;
        try {
            System.out.println("FileUpdater started...");

            // Continuously check for new lines
            while (true) {
                try {
                    // Create a new RandomAccessFile for each iteration
                    file = new RandomAccessFile(logFileReader.getLogFile(), "r");

                    // Seek to the last known position
                    file.seek(logFileReader.getLogFilePointer());

                    String line;
                    // Read new lines if they exist
                    while ((line = file.readLine()) != null) {
                        System.out.println("New line detected: " + line);
                        webSocketServer.sendUpdate(line);
                        logFileReader.setLogFilePointer(file.getFilePointer());
                        System.out.println("Updated file pointer: " + logFileReader.getLogFilePointer());
                    }

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    // Close and clean up the RandomAccessFile
                    if (file != null) {
                        try {
                            file.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // Sleep for a short period before checking again
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
