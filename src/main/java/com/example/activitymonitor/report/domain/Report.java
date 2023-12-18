package com.example.activitymonitor.report.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Report {

    protected String title;
    protected LocalDateTime timestamp;
    protected Duration duration;

    protected List<DataPoint> data;
    protected String summary;
    protected ReportType reportType;

    public Report(String title, Duration duration, ReportType reportType) {
        this.title = title;
        this.timestamp = LocalDateTime.now();
        this.duration = duration;
        this.data = new ArrayList<>();
        this.reportType = reportType;
    }
}

