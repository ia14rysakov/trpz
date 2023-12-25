package com.example.activitymonitor.report.domain;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Report {

    protected String title;
    protected LocalDateTime timestamp;
    protected Duration duration;

    protected List<MonitoringPoint> data;
    protected String summary;
    protected String reportType;

    public Report(String title, Duration duration, String reportType) {
        this.title = title;
        this.timestamp = LocalDateTime.now();
        this.duration = duration;
        this.data = new ArrayList<>();
        this.reportType = reportType;
    }
}

