package com.example.activitymonitor.monitoring.application;

import com.example.activitymonitor.report.domain.Report;

import java.time.LocalDateTime;

public interface Monitoring {
    void accept(Visitor visitor);

    void startMonitoring(boolean isMonitoringStarted);

    Report generateReportByTime(LocalDateTime end);

    Report generateScheduledReport(LocalDateTime start, LocalDateTime end);

    Report startReporting();

    Report stopReporting();
}

