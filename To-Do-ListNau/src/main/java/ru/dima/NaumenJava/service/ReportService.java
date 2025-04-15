package ru.dima.naumenjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dima.naumenjava.entity.Report;
import ru.dima.naumenjava.entity.ReportStatus;
import ru.dima.naumenjava.entity.Task;
import ru.dima.naumenjava.repository.ReportRepository;
import ru.dima.naumenjava.repository.TaskRepository;
import ru.dima.naumenjava.repository.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public String getReportContent(Long id) {
        return reportRepository.findById(id)
                .map(Report::getContent)
                .orElse("Report not found");
    }

    @Transactional
    public Long createReport() {
        Report report = new Report();
        report.setContent(" ");
        report.setStatus(ReportStatus.CREATED);
        reportRepository.save(report);
        return report.getId();
    }

    public Report getReportById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with ID: " + id));
    }

    @Async
    public void generateReportAsync(Long reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow();

        try {
            long start = System.currentTimeMillis();

            CompletableFuture<Integer> userCountFuture = CompletableFuture.supplyAsync(() -> {
                long startTime = System.currentTimeMillis();
                int count = userRepository.findAll().size();
                long elapsed = System.currentTimeMillis() - startTime;
                report.setUserCountTime(elapsed);
                return count;
            });

            CompletableFuture<List<Task>> tasksFuture = CompletableFuture.supplyAsync(() -> {
                long startTime = System.currentTimeMillis();
                List<Task> tasks = taskRepository.findAll();
                long elapsed = System.currentTimeMillis() - startTime;
                report.setEntityListTime(elapsed);
                return tasks;
            });

            int userCount = userCountFuture.join();
            List<Task> taskList = tasksFuture.join();

            StringBuilder html = new StringBuilder();
            html.append("<html><body>");
            html.append("<h1>App Report</h1>");
            html.append("<p>Number of users: ").append(userCount).append("</p>");
            html.append("<h2>Task list:</h2>");
            html.append("<ul>");
            for (Task task : taskList) {
                html.append("<li>").append(task.toString()).append("</li>");
            }
            html.append("</ul>");
            html.append("<p>User count time: ").append(report.getUserCountTime()).append("ms</p>");
            html.append("<p>Task fetch time: ").append(report.getEntityListTime()).append("ms</p>");
            html.append("<p>Total report time: ").append(System.currentTimeMillis() - start).append("ms</p>");
            html.append("</body></html>");

            report.setContent(html.toString());
            report.setStatus(ReportStatus.COMPLETED);
            report.setTotalTime(System.currentTimeMillis() - start);
        } catch (Exception e) {
            report.setStatus(ReportStatus.ERROR);
            report.setContent("Error during report generation: " + e.getMessage());
        }

        reportRepository.save(report);
    }
}
