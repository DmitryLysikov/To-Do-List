package ru.dima.naumenjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dima.naumenjava.criteria.TaskCriteriaRepositoryImpl;
import ru.dima.naumenjava.entity.Task;

import java.util.List;

@RestController
@RequestMapping("/controller/taskCR")
public class TaskCRController {
    @Autowired
    private TaskCriteriaRepositoryImpl taskCriteriaRepository;

    @GetMapping("/findByStatusAndPriority")
    public List<Task> findByStatusAndPriority(@RequestParam String status, @RequestParam Long priority) {
        return taskCriteriaRepository.findByStatusAndPriority(status, priority);
    }

    @GetMapping("/findTasksByUserId/{userId}")
    public List<Task> findTasksByUserId(@PathVariable Long userId) {
        return taskCriteriaRepository.findTasksByUserId(userId);
    }
}
