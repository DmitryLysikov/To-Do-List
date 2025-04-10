package ru.dima.naumenjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dima.naumenjava.entity.Task;
import ru.dima.naumenjava.repository.TaskRepository;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public String getTasks(Model model) {
        List<Task> tasks = (List<Task>) taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }
}
