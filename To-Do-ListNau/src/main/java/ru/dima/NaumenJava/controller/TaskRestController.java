package ru.dima.naumenjava.controller;

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

    @GetMapping("/rest/tasks")
    public List<Task> findByStatusAndPriority(@RequestParam(name = "status", required = false) String status,
                                              @RequestParam(name = "priority", required = false) Long priority)
    {
        return taskCriteriaRepository.findByStatusAndPriority(status, priority);
    }

    @GetMapping("/find-tasks-by-userid/{userId}")
    public List<Task> findTasksByUserId(@PathVariable Long userId) {
        if(!userRepository.existsById(userId)){
            throw new UserNotFoundException("User not found");
        }else {
            return taskCriteriaRepository.findTasksByUserId(userId);
        }
    }
}
