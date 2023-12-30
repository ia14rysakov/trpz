package com.example.activitymonitor.report.application.visitor;

import com.example.activitymonitor.monitoring.application.service.*;
import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.infrastructure.rest.dto.ReportRequestDto;
import reactor.core.publisher.Mono;

public interface ReportVisitor {

    String getReportName();
    Mono<Report> visit(CpuLoadMonitoringService cpuLoadMonitoringService, ReportRequestDto reportRequestDto);

    Mono<Report> visit(KeyLoggerMonitoringService keyLoggerMonitoringService, ReportRequestDto reportRequestDto);

    Mono<Report> visit(MemoryMonitoringService memoryMonitoringService, ReportRequestDto reportRequestDto);

    Mono<Report> visit(MouseTrackerMonitoringService mouseTrackerMonitoringService, ReportRequestDto reportRequestDto);

    Mono<Report> visit(WindowsMonitoringService windowsMonitoringService, ReportRequestDto reportRequestDto);

    Mono<Report> visit(TestMonitoring testMonitoring, ReportRequestDto reportRequestDto);
}

