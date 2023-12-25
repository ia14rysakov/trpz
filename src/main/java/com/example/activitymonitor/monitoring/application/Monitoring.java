package com.example.activitymonitor.monitoring.application;

import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;

public interface Monitoring {

    String getMonitoringName();
    Report accept(ReportVisitor reportVisitor);

    void startMonitoring(boolean isMonitoringStarted);
}

