package com.example.activitymonitor.report.application.visitor;

import com.example.activitymonitor.monitoring.application.service.*;
import com.example.activitymonitor.report.domain.Report;
import reactor.core.publisher.Mono;

public interface ReportVisitor {

    String getReportName();
    Mono<Report> visit(CpuLoadMonitoringService cpuLoadMonitoringService);

    Mono<Report> visit(KeyLoggerMonitoringService keyLoggerMonitoringService);

    Mono<Report> visit(MemoryMonitoringService memoryMonitoringService);

    Mono<Report> visit(MouseTrackerMonitoringService mouseTrackerMonitoringService);

    Mono<Report> visit(WindowsMonitoringService windowsMonitoringService);
}
