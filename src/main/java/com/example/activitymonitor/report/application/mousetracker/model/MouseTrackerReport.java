package com.example.activitymonitor.report.application.mousetracker.model;

import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.domain.ReportType;

import java.time.Duration;

public class MouseTrackerReport extends Report {

    public MouseTrackerReport(String title, Duration duration, ReportType reportType) {
        super(title, duration, reportType);
    }
}
