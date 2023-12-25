package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;

public class KeyLoggerMonitoringService implements Monitoring {

    @Override
    public Report accept(ReportVisitor reportVisitor) {
        return reportVisitor.visit(this);
    }

    @Override
    public void startMonitoring(boolean isMonitoringStarted){
        //TODO
    }
}
