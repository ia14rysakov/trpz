package com.example.activitymonitor.report.application.visitor;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.service.*;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.report.domain.Report;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Component
public class ReportStartStopGenerator implements ReportVisitor {

    private boolean isMonitoringStarted = false;

    @Override
    public String getReportName() {
        return "ReportStartStop";
    }

    @Override
    public Mono<Report> visit(CpuLoadMonitoringService cpuLoadMonitoringService) {
        return defaultVisiting(cpuLoadMonitoringService, "CPU Load Monitoring");
    }

    @Override
    public Mono<Report> visit(KeyLoggerMonitoringService keyLoggerMonitoringService) {
        return defaultVisiting(keyLoggerMonitoringService, "Key Logger Monitoring");
    }

    @Override
    public Mono<Report> visit(MemoryMonitoringService memoryMonitoringService) {
        return defaultVisiting(memoryMonitoringService, "Memory Monitoring");
    }

    @Override
    public Mono<Report> visit(MouseTrackerMonitoringService mouseTrackerMonitoringService) {
        return defaultVisiting(mouseTrackerMonitoringService, "Mouse Tracker Monitoring");
    }

    @Override
    public Mono<Report> visit(WindowsMonitoringService windowsMonitoringService) {
        return defaultVisiting(windowsMonitoringService, "Windows Monitoring");
    }

    private Mono<Report> defaultVisiting(Monitoring monitoring, String serviceName) {
        LocalDateTime startTime = LocalDateTime.now();

        return monitoring.startMonitoring(true)
                .takeWhile(point -> isMonitoringStarted)
                .doOnError(e -> {
                    //TODO log exception
                })
                .collectList()
                .map(data -> {
                    LocalDateTime endTime = LocalDateTime.now();
                    Duration duration = Duration.between(startTime, endTime);

                    String title = serviceName + " Report - from " + startTime + " to " + endTime;

                    Report report = new Report(title, duration, getReportName());
                    report.setData(data);

                    return report;
                });
    }
}
