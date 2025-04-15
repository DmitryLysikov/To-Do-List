package ru.dima.naumenjava.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(name = "user_count_time")
    private Long userCountTime;

    @Column(name = "entity_list_time")
    private Long entityListTime;

    @Column(name = "created_at")
    private Long totalTime;

    public Report() {
    }

    public Report(ReportStatus status, String content) {
        this.status = status;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserCountTime() {
        return userCountTime;
    }

    public void setUserCountTime(Long userCountTime) {
        this.userCountTime = userCountTime;
    }

    public Long getEntityListTime() {
        return entityListTime;
    }

    public void setEntityListTime(Long entityListTime) {
        this.entityListTime = entityListTime;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }
}
