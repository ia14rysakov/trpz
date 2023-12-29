package com.example.activitymonitor.report.application.visitor;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.service.*;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.report.domain.Report;
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

    private LocalDateTime dueToTime = LocalDateTime.now().plusSeconds(20);

    @Override
    public String getReportName() {
        return "ReportByTime";
    }

    @Override
    public Mono<Report> visit(CpuLoadMonitoringService cpuLoadMonitoringService) {
        return commonVisitor(cpuLoadMonitoringService, "CPU Load Monitoring");
    }

    @Override
    public Mono<Report> visit(KeyLoggerMonitoringService keyLoggerMonitoringService) {
        return commonVisitor(keyLoggerMonitoringService, "Key Logger Monitoring");
    }

    @Override
    public Mono<Report> visit(MemoryMonitoringService memoryMonitoringService) {
        return commonVisitor(memoryMonitoringService, "Memory Monitoring");
    }

    @Override
    public Mono<Report> visit(MouseTrackerMonitoringService mouseTrackerMonitoringService) {

        return commonVisitor(mouseTrackerMonitoringService, "Mouse Tracker Monitoring");
    }

    @Override
    public Mono<Report> visit(WindowsMonitoringService windowsMonitoringService) {

        return commonVisitor(windowsMonitoringService, "Windows Monitoring");
    }

    @Override
    public Mono<Report> visit(TestMonitoring testMonitoring) {
        return commonVisitor(testMonitoring, "Test Monitoring");
    }

    private Mono<Report> commonVisitor(Monitoring monitoringService, String serviceName) {
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
