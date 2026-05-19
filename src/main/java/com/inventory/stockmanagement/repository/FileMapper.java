package com.inventory.stockmanagement.repository;

public interface FileMapper<T> {
    String toLine(T entity);
    T fromLine(String line);
}
