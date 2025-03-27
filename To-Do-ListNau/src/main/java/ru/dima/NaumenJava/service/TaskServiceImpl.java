package ru.dima.NaumenJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dima.NaumenJava.Task.Task;
import ru.dima.NaumenJava.dao.TaskRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void createTask(Long id, String status, String taskName, LocalDate deadlines) {
        Task task = new Task(id, status, taskName, deadlines);
        taskRepository.create(task);
    }

    @Override
    public Task readById(Long id) {
        return taskRepository.read(id);
    }

    @Override
    public void updateTask(Long id, String newStatus, String newTaskName, LocalDate newDeadlines) {
        Task updateTask = new Task(id, newStatus, newTaskName, newDeadlines);
        taskRepository.update(updateTask);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.delete(id);
    }

    @Override
    public List<Task> readAllTasks() {
        return taskRepository.readAll();
    }
}
