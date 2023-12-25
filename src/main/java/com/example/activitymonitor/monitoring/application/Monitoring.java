package com.example.activitymonitor.monitoring.application;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

public interface Monitoring {

    String getMonitoringName();
    Report accept(ReportVisitor reportVisitor);

    Flux<MonitoringPoint> startMonitoring(boolean isMonitoringStarted);
}

