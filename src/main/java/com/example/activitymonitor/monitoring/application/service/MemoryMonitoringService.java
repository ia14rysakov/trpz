package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;

public class MemoryMonitoringService implements Monitoring {

    @Override
    public String getMonitoringName() {
        return "MemoryMonitoring";
    }

    @Override
    public Report accept(ReportVisitor reportVisitor) {
        return reportVisitor.visit(this);
    }

    @Override
    public void startMonitoring(boolean isMonitoringStarted) {
        //TODO start monitoring
    }
}
