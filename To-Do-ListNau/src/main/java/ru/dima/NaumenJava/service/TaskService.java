package ru.dima.NaumenJava.service;

import ru.dima.NaumenJava.Task.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    void createTask(Long id, String status, String taskName, LocalDate deadlines);
    Task readById(Long id);
    void updateTask(Long id, String newStatus, String newTaskName, LocalDate newDeadlines);
    void deleteTask(Long id);
    List<Task> readAllTasks();
}
