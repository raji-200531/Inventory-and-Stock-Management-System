package com.inventory.stockmanagement.repository;

import com.inventory.stockmanagement.model.base.BaseEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public abstract class GenericFileRepository<T extends BaseEntity> {
    private final Path filePath;
    private final FileMapper<T> mapper;

    protected GenericFileRepository(String storageDir, String fileName, FileMapper<T> mapper) {
        this.filePath = Path.of(storageDir, fileName);
        this.mapper = mapper;
        try {
            Files.createDirectories(filePath.getParent());
            if (Files.notExists(filePath)) Files.createFile(filePath);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot create storage file: " + filePath, e);
        }
    }

    public synchronized List<T> findAll() {
        try {
            List<T> data = new ArrayList<>();
            for (String line : Files.readAllLines(filePath, StandardCharsets.UTF_8)) {
                if (!line.isBlank() && !line.trim().startsWith("#")) data.add(mapper.fromLine(line));
            }
            data.sort(Comparator.comparing(BaseEntity::getId, Comparator.nullsLast(Long::compareTo)));
            return data;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read storage file: " + filePath, e);
        }
    }

    public synchronized Optional<T> findById(Long id) {
        return findAll().stream().filter(e -> e.getId() != null && e.getId().equals(id)).findFirst();
    }

    public synchronized T save(T entity) {
        List<T> data = findAll();
        if (entity.getId() == null) {
            long max = data.stream().map(BaseEntity::getId).filter(id -> id != null).max(Long::compareTo).orElse(0L);
            entity.setId(max + 1);
            entity.markCreated();
            data.add(entity);
        } else {
            boolean updated = false;
            for (int i = 0; i < data.size(); i++) {
                if (entity.getId().equals(data.get(i).getId())) {
                    if (entity.getCreatedAt() == null) entity.setCreatedAt(data.get(i).getCreatedAt());
                    entity.markUpdated();
                    data.set(i, entity);
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                entity.markCreated();
                data.add(entity);
            }
        }
        writeAll(data);
        return entity;
    }

    public synchronized void deleteById(Long id) {
        List<T> data = findAll();
        data.removeIf(e -> e.getId() != null && e.getId().equals(id));
        writeAll(data);
    }

    private void writeAll(List<T> data) {
        try {
            Files.write(filePath, data.stream().map(mapper::toLine).toList(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write storage file: " + filePath, e);
        }
    }

    protected static String s(Object v) { return v == null ? "" : String.valueOf(v); }
    protected static Long l(String v) { return v == null || v.isBlank() ? null : Long.parseLong(v); }
    protected static int i(String v) { return v == null || v.isBlank() ? 0 : Integer.parseInt(v); }
    protected static boolean b(String v) { return v == null || v.isBlank() || Boolean.parseBoolean(v); }
    protected static LocalDateTime dt(String v) { return v == null || v.isBlank() ? LocalDateTime.now() : LocalDateTime.parse(v); }
}
