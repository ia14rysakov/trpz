package com.example.activitymonitor.monitoring.application;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.infrastructure.rest.dto.ReportRequestDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Monitoring {

    String getMonitoringName();
    Mono<Report> accept(ReportVisitor reportVisitor, ReportRequestDto reportRequestDto);

    Flux<MonitoringPoint> startMonitoring(boolean isMonitoringStarted);
}

