package com.example.activitymonitor.report.application.windows.model;

import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.domain.ReportType;

import java.time.Duration;

public class WindowsReport extends Report {

    public WindowsReport(String title, Duration duration, String reportType) {
        super(title, duration, reportType);
    }
}

