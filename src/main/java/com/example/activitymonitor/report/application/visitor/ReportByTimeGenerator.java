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

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@Component
public class ReportByTimeGenerator implements ReportVisitor {

    private LocalDateTime dueToTime = LocalDateTime.now().plusMinutes(1);

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

    private Mono<Report> commonVisitor(Monitoring monitoringService, String serviceName) {
        if (dueToTime == null) {
            throw new IllegalStateException("dueToTime must be set before generating a report");
        }

        LocalDateTime startTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, dueToTime);

        Flux<MonitoringPoint> dataFlux = monitoringService.startMonitoring(true)
                .takeWhile(point -> LocalDateTime.now().isBefore(dueToTime));

        return dataFlux.collectList().map(data -> {
            String title = serviceName + " Report - from " + startTime + " to " + dueToTime;
            Report report = new Report(title, duration, getReportName());
            report.setData(data);
            return report;
        });
    }
}
