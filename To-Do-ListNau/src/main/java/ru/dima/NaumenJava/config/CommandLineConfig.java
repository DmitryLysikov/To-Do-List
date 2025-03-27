package ru.dima.naumenjava.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class CommandLineConfig {
//    @Bean
    public CommandLineRunner commandScanner(CommandProcessor commandProcessor, ApplicationContext context) {
        return args ->
        {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Введите команду. 'create id status taskName deadlines'- создать задачу.");
                System.out.println("Введите команду. 'read id'- вывести конкретную задачу.");
                System.out.println("Введите команду. 'readall'- вывести все задачи.");
                System.out.println("Введите команду. 'update id newStatus newTaskName newDeadlines'- обновить задачу.");
                System.out.println("Введите команду. 'delete id'- удалить задачу.");
                System.out.println("Введите команду. 'exit'- для выхода.");
                while (true) {
                    // Показать приглашение для ввода
                    System.out.print("> ");
                    String input = scanner.nextLine();
                    // Выход из цикла, если введена команда "exit"
                    if ("exit".equalsIgnoreCase(input.trim())) {
                        System.out.println("Выход из программы...");
                        System.exit(SpringApplication.exit(context));
                        break;
                    } // Обработка команды
                    commandProcessor.processCommand(input);
                }
            }
        };
    }
}
