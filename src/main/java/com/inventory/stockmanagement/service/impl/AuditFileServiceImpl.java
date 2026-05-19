package com.inventory.stockmanagement.service.impl;

import com.inventory.stockmanagement.service.AuditFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AuditFileServiceImpl implements AuditFileService {
    private final Path auditFile;

    public AuditFileServiceImpl(@Value("${app.storage-dir:data}") String dir) {
        this.auditFile = Path.of(dir, "audit-log.txt");
        try {
            Files.createDirectories(auditFile.getParent());
            if (Files.notExists(auditFile)) Files.createFile(auditFile);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot create audit log", e);
        }
    }

    public synchronized void log(String module, String action, String details) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String line = time + " | " + module + " | " + action + " | " + details + System.lineSeparator();
        try {
            Files.writeString(auditFile, line, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write audit log", e);
        }
    }
}
