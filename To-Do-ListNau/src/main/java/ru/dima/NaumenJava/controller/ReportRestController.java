package ru.dima.naumenjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dima.naumenjava.entity.Report;
import ru.dima.naumenjava.entity.ReportStatus;
import ru.dima.naumenjava.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/generate")
    @ResponseBody
    public String generateReport() {
        Long reportId = reportService.createReport();
        reportService.generateReportAsync(reportId);
        return "Report generation started. Report ID: " + reportId;
    }

    @GetMapping("/{id}")
    public String getReportContent(@PathVariable Long id) {
        Report report = reportService.getReportById(id);

        if (report.getStatus() == ReportStatus.CREATED) {
            return "Report is still being generated. Please check back later.";
        } else if (report.getStatus() == ReportStatus.ERROR) {
            return "Report generation failed: " + report.getContent();
        } else {
            return report.getContent();
        }
    }
}
