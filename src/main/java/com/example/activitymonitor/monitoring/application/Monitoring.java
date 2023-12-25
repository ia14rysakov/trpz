package com.example.activitymonitor.monitoring.application;

import com.example.activitymonitor.monitoring.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;

public interface Monitoring {
    Report accept(ReportVisitor reportVisitor);

    void startMonitoring(boolean isMonitoringStarted);
}

