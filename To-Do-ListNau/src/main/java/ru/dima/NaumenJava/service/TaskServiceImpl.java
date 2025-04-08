package ru.dima.naumenjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dima.naumenjava.entity.Task;
import ru.dima.naumenjava.repository.CommentRepository;
import ru.dima.naumenjava.repository.TaskRepository;
import ru.dima.naumenjava.repository.StatusRepository;
import ru.dima.naumenjava.repository.UserRepository;
import ru.dima.naumenjava.repository.CategoryRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final StatusRepository statusRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           CommentRepository commentRepository,
                           UserRepository userRepository,
                           CategoryRepository categoryRepository,
                           StatusRepository statusRepository) {
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.statusRepository = statusRepository;
    }

    public void createTask(Long priority, String description, String taskName, LocalDate deadlines) {
        Task task = new Task(priority, description, taskName, deadlines);
        taskRepository.save(task);
    }

    public Optional<Task> readById(Long id) {
        return taskRepository.findById(id);
    }

    public void updateTask(Long id, Long priority, String newDescription, String newTaskName, LocalDate newDeadlines) {
        Task updateTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

        updateTask.setPriority(priority);
        updateTask.setDescription(newDescription);
        updateTask.setTaskName(newTaskName);
        updateTask.setDeadlines(newDeadlines);

        taskRepository.save(updateTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> readAllTasks() {
        return taskRepository.findAll();
    }
}
