package ru.dima.NaumenJava.dao;

import io.micrometer.common.lang.Nullable;

import java.util.List;

public interface CrudRepository <T, ID>{
    void create(T task);
    @Nullable
    T read(ID id);
    void update(T updatedTask);
    void delete(ID id);
    List<T> readAll();
}