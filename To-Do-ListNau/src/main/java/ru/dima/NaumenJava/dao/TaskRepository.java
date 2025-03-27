package ru.dima.NaumenJava.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.dima.NaumenJava.Task.Task;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository implements CrudRepository<Task, Long> {
    private final List<Task> tasks;

    @Autowired
    public TaskRepository(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void create(Task task) {
        tasks.add(task);
    }

    @Override
    public Task read(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Task updatedTask) {
        Optional<Task> taskOptional = Optional.ofNullable(read(updatedTask.getId()));

        taskOptional.ifPresent(task -> {
            task.setTaskName(updatedTask.getTaskName());
            task.setStatus(updatedTask.getStatus());
            task.setDeadlines(updatedTask.getDeadlines());
        });
    }

    /**
     * Удаляет задачу с указанным идентификатором. Если задача с таким ID не существует,
     * метод завершится без ошибок и никаких действий выполнено не будет.
     *
     * @param id идентификатор задачи для удаления
     */
    @Override
    public void delete(Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }

    @Override
    public List<Task> readAll() {
        return Collections.unmodifiableList(tasks);
    }
}
