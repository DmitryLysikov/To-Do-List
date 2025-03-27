package ru.dima.NaumenJava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.dima.NaumenJava.Task.Task;
import ru.dima.NaumenJava.service.TaskService;

import java.time.LocalDate;
import java.util.List;

@Component
public class CommandProcessor {
    private final TaskService taskService;

    @Autowired
    public CommandProcessor(TaskService taskService) {
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
                Task task = taskService.readById(Long.valueOf(cmd[1]));
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
                taskService.updateTask(Long.valueOf(cmd[1]), cmd[2], cmd[3], LocalDate.parse(cmd[4]));
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
