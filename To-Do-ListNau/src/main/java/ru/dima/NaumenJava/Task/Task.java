package ru.dima.NaumenJava.Task;

import java.time.LocalDate;

public class Task {
    private Long id;
    private String status;
    private String taskName;
    private LocalDate deadlines;

    public Task(Long id, String status, String taskName, LocalDate deadlines){
        this.id = id;
        this.status = status;
        this.taskName = taskName;
        this.deadlines = deadlines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDate getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(LocalDate deadlines) {
        this.deadlines = deadlines;
    }

    @Override
    public String toString() {
        return "Task{ id= " + id +
                ", title= " + taskName +
                ", status= " + status + ", dueDate= " + deadlines + '}';
    }
}