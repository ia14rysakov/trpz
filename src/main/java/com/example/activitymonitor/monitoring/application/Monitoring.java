package com.example.activitymonitor.monitoring.application;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;

import java.util.stream.Stream;

public interface Monitoring {

    String getMonitoringName();
    Report accept(ReportVisitor reportVisitor);

    Stream<MonitoringPoint> startMonitoring(boolean isMonitoringStarted);
}

