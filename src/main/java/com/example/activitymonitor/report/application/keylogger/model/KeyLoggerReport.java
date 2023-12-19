package com.example.activitymonitor.report.application.keylogger.model;

import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.domain.ReportType;

import java.time.Duration;

public class KeyLoggerReport extends Report {
    public KeyLoggerReport(String title, Duration duration, ReportType reportType) {
        super(title, duration, reportType);
    }
}
