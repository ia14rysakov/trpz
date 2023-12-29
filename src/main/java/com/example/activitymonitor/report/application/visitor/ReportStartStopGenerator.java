package com.example.activitymonitor.report.application.visitor;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.service.*;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.infrastructure.rest.dto.ReportRequestDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Setter
@Getter
@Component
public class ReportStartStopGenerator implements ReportVisitor {

    private final Map<String, ConnectableFlux<MonitoringPoint>> ongoingReports = new ConcurrentHashMap<>();
    private final Map<String, List<MonitoringPoint>> collectedData = new ConcurrentHashMap<>();

    @Override
    public String getReportName() {
        return "ReportStartStop";
    }

    @Override
    public Mono<Report> visit(CpuLoadMonitoringService cpuLoadMonitoringService, ReportRequestDto reportRequestDto) {
        return defaultVisiting(cpuLoadMonitoringService, "CPU Load Monitoring", reportRequestDto.isReportGoing());
    }

    @Override
    public Mono<Report> visit(KeyLoggerMonitoringService keyLoggerMonitoringService, ReportRequestDto reportRequestDto) {
        return defaultVisiting(keyLoggerMonitoringService, "Key Logger Monitoring", reportRequestDto.isReportGoing());
    }

    @Override
    public Mono<Report> visit(MemoryMonitoringService memoryMonitoringService, ReportRequestDto reportRequestDto) {
        return defaultVisiting(memoryMonitoringService, "Memory Monitoring", reportRequestDto.isReportGoing());
    }

    @Override
    public Mono<Report> visit(MouseTrackerMonitoringService mouseTrackerMonitoringService, ReportRequestDto reportRequestDto) {
        return defaultVisiting(mouseTrackerMonitoringService, "Mouse Tracker Monitoring", reportRequestDto.isReportGoing());
    }

    @Override
    public Mono<Report> visit(WindowsMonitoringService windowsMonitoringService, ReportRequestDto reportRequestDto) {
        return defaultVisiting(windowsMonitoringService, "Windows Monitoring", reportRequestDto.isReportGoing());
    }

    @Override
    public Mono<Report> visit(TestMonitoring testMonitoring, ReportRequestDto reportRequestDto) {
        return defaultVisiting(testMonitoring, "Test Monitoring", reportRequestDto.isReportGoing());
    }

    private Mono<Report> defaultVisiting(Monitoring monitoring, String serviceName, boolean isReportGoing) {
        if (isReportGoing) {
            return startMonitoring(monitoring, serviceName).log();
        } else {
            return generateReport(monitoring, serviceName).log();
        }
    }


    private Mono<Report> startMonitoring(Monitoring monitoring, String reportKey) {
        ConnectableFlux<MonitoringPoint> connectableFlux = monitoring.startMonitoring(true)
                .publish();

        connectableFlux.connect();
        ongoingReports.put(reportKey, connectableFlux);

        List<MonitoringPoint> points = new CopyOnWriteArrayList<>();
        collectedData.put(reportKey, points);
        connectableFlux.subscribe(points::add);

        return Mono.empty(); // Report generation is ongoing
    }

    private Mono<Report> generateReport(Monitoring monitoring, String reportKey) {
        ConnectableFlux<MonitoringPoint> connectableFlux = ongoingReports.remove(reportKey);
//        if (connectableFlux != null) {
//            connectableFlux.con();
//        }

        List<MonitoringPoint> points = collectedData.remove(reportKey);
        if (points == null || points.isEmpty()) {
            return Mono.empty(); // No data to generate report
        }

        return Mono.just(new Report(
                "title",
                Duration.between(LocalDateTime.now(), LocalDateTime.now()),
                getReportName(),
                points));
    }
}
