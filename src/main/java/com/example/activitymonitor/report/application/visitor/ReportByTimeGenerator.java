package com.example.activitymonitor.report.application.visitor;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.service.*;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.infrastructure.rest.dto.ReportRequestDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Getter
@Setter
@Component
public class ReportByTimeGenerator implements ReportVisitor {

    private Logger logger = Logger.getLogger(ReportByTimeGenerator.class.getName());

    @Override
    public String getReportName() {
        return "ReportByTime";
    }

    @Override
    public Mono<Report> visit(CpuLoadMonitoringService cpuLoadMonitoringService, ReportRequestDto reportRequestDto) {
        return commonVisitor(cpuLoadMonitoringService, "CPU Load Monitoring", reportRequestDto.getDueToTime());
    }

    @Override
    public Mono<Report> visit(KeyLoggerMonitoringService keyLoggerMonitoringService, ReportRequestDto reportRequestDto) {
        return commonVisitor(keyLoggerMonitoringService, "Key Logger Monitoring", reportRequestDto.getDueToTime());
    }

    @Override
    public Mono<Report> visit(MemoryMonitoringService memoryMonitoringService, ReportRequestDto reportRequestDto) {
        return commonVisitor(memoryMonitoringService, "Memory Monitoring", reportRequestDto.getDueToTime());
    }

    @Override
    public Mono<Report> visit(MouseTrackerMonitoringService mouseTrackerMonitoringService, ReportRequestDto reportRequestDto) {

        return commonVisitor(mouseTrackerMonitoringService, "Mouse Tracker Monitoring", reportRequestDto.getDueToTime());
    }

    @Override
    public Mono<Report> visit(WindowsMonitoringService windowsMonitoringService, ReportRequestDto reportRequestDto) {

        return commonVisitor(windowsMonitoringService, "Windows Monitoring", reportRequestDto.getDueToTime());
    }

    @Override
    public Mono<Report> visit(TestMonitoring testMonitoring, ReportRequestDto reportRequestDto) {
        return commonVisitor(testMonitoring, "Test Monitoring", reportRequestDto.getDueToTime());
    }

    private Mono<Report> commonVisitor(Monitoring monitoringService, String serviceName, LocalDateTime dueToTime) {
        if (dueToTime == null) {
            throw new IllegalStateException("dueToTime must be set before generating a report");
        }
        else if (dueToTime.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("dueToTime must be in the future");
        }

        logger.info("Generating report for " + serviceName + " from " + LocalDateTime.now() + " to " + dueToTime);

        LocalDateTime startTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, dueToTime);

        Flux<MonitoringPoint> dataFlux = monitoringService.startMonitoring(true)
                .publishOn(Schedulers.boundedElastic())
                .takeWhile(it -> Duration.between(startTime, LocalDateTime.now()).compareTo(duration) < 0).log();


        return dataFlux.collectList().map(dataList -> new Report(
                serviceName + " Report - from " + startTime + " to " + dueToTime,
                duration,
                getReportName(),
                dataList));
    }
}
