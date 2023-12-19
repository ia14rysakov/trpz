package com.example.activitymonitor.report.application.memory.model;

import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.domain.ReportType;

import java.time.Duration;

public class MemoryReport extends Report {

    public MemoryReport(String title, Duration duration, ReportType reportType) {
        super(title, duration, reportType);
    }
}
