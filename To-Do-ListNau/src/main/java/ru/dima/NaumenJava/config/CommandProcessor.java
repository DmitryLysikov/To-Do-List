package ru.dima.naumenjava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.dima.naumenjava.entity.Task;
import ru.dima.naumenjava.service.TaskServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class CommandProcessor {
    private final TaskServiceImpl taskService;

    @Autowired
    public CommandProcessor(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    public void processCommand(String command) {
        String[] cmd = command.split(" ");
        switch (cmd[0]) {
            case "create" -> {
                taskService.createTask(Long.valueOf(cmd[1]), cmd[2], cmd[3], LocalDate.parse(cmd[4]));
                System.out.println("Task created");
            }
            case "read" -> {
                Optional<Task> task = taskService.readById(Long.valueOf(cmd[1]));
                System.out.println(task);
            }
            case "readall" -> {
                List<Task> tasks = taskService.readAllTasks();
                if(tasks.isEmpty()){
                    System.out.println("No tasks found");
                }else {
                    tasks.forEach(System.out::println);
                }
            }
            case "update" -> {
                taskService.updateTask(Long.valueOf(cmd[0]), Long.valueOf(cmd[1]), cmd[2], cmd[3],
                        LocalDate.parse(cmd[4]));
                System.out.println("Task updated");
            }
            case "delete" -> {
                taskService.deleteTask(Long.valueOf(cmd[1]));
                System.out.println("Task deleted");
            }
            default -> {
                System.out.println("Invalid command");
            }
        }
    }
}
