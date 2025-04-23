package ru.dima.naumenjava.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dima.naumenjava.criteria.TaskCriteriaRepositoryImpl;
import ru.dima.naumenjava.entity.Task;
import ru.dima.naumenjava.exception.UserNotFoundException;
import ru.dima.naumenjava.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/rest/tasks")
public class TaskRestController {
    private final TaskCriteriaRepositoryImpl taskCriteriaRepository;
    private final UserRepository userRepository;

    public TaskRestController(TaskCriteriaRepositoryImpl taskCriteriaRepository, UserRepository userRepository) {
        this.taskCriteriaRepository = taskCriteriaRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Task>> findByStatusAndPriority(@RequestParam(name = "status", required = false) String status,
                                              @RequestParam(name = "priority", required = false) Long priority)
    {
        List<Task> tasks = taskCriteriaRepository.findByStatusAndPriority(status, priority);
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/find-tasks-by-userid/{userId}")
    public ResponseEntity<List<Task>> findTasksByUserId(@PathVariable Long userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build(); // 404
        }

        List<Task> tasks = taskCriteriaRepository.findTasksByUserId(userId);

        return ResponseEntity.ok(tasks); // 200
    }
}
