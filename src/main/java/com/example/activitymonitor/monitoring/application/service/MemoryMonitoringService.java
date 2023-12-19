package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.Visitor;
import com.example.activitymonitor.report.domain.Report;

import java.time.LocalDateTime;

public class MemoryMonitoringService implements Monitoring {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void startMonitoring(boolean isMonitoringStarted) {
        //TODO start monitoring
    }

    @Override
    public Report generateReportByTime(LocalDateTime end) {
        return null;
    }

    @Override
    public Report generateScheduledReport(LocalDateTime start, LocalDateTime end) {
        return null;
    }

    @Override
    public Report startReporting() {
        return null;
    }

    @Override
    public Report stopReporting() {
        return null;
    }
}
