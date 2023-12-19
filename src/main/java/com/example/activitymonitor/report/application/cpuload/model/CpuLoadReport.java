package com.example.activitymonitor.report.application.cpuload.model;

import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.domain.ReportType;

import java.time.Duration;

public class CpuLoadReport extends Report {
    public CpuLoadReport(String title, Duration duration, ReportType reportType) {
        super(title, duration, reportType);
    }
}
