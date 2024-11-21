package com.webhook.notifiation.api.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.webhook.notifiation.api.model.NotificationRequestDto;
import com.webhook.notifiation.api.utils.SecurityUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ProcessNotification {

    @Autowired
    private SecurityUtils securityUtils;

    private static final String RESOURCE_PATH = "src/main/resources/notification/";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void processMercantilNotification(NotificationRequestDto notificationRequest) {
        Gson gson = new Gson();
        String decryptedJson = securityUtils.decrypt(notificationRequest.getData());
        
        if (decryptedJson != null) {
            try {
                writeToFile(decryptedJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Failed to decrypt notification request.");
        }
    }

    private void writeToFile(String data) throws IOException {
        String currentDate = LocalDate.now().format(DATE_FORMATTER);
        String fileName = RESOURCE_PATH + "notifications-" + currentDate + ".log";

        // Crea el directorio si no existe
        File directory = new File(RESOURCE_PATH);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (dirCreated) {
                System.out.println("Directory created: " + RESOURCE_PATH);
            }
        }

        // Crea el archivo si no existe
        File file = new File(fileName);
        if (!file.exists()) {
            boolean fileCreated = file.createNewFile();
            if (fileCreated) {
                System.out.println("File created: " + fileName);
            }
        }

        // Escribir en el archivo
        String timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        String logEntry = String.format("[%s] %s", timestamp, data);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(logEntry);
            writer.newLine();
        }
    }
}
