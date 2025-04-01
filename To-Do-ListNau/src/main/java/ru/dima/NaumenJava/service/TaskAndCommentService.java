package ru.dima.naumenjava.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dima.naumenjava.entity.*;
import ru.dima.naumenjava.repository.*;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskAndCommentService {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final StatusRepository statusRepository;

    public TaskAndCommentService(TaskRepository taskRepository,
                                 CommentRepository commentRepository,
                                 UserRepository userRepository,
                                 CategoryRepository categoryRepository,
                                 StatusRepository statusRepository)
    {
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.statusRepository = statusRepository;
    }

    @Transactional
    public Task createTaskWithComments(Long userId,
                                       Long categoryId,
                                       Long statusId,
                                       String taskName,
                                       String description,
                                       Long priority,
                                       LocalDate deadline,
                                       List<String> commentTexts)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        Task task = new Task();
        task.setTaskName(taskName);
        task.setDescription(description);
        task.setDeadlines(deadline);
        task.setPriority(priority);
        task.setUser(user);
        task.setCategory(category);
        task.setStatus(status);

        Task savedTask = taskRepository.save(task);

        for (String text : commentTexts) {
            Comment comment = new Comment();
            comment.setText(text);
            comment.setTask(savedTask);
            comment.setUser(user);

            commentRepository.save(comment);
        }

        return savedTask;
    }
}
