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

import java.time.Duration;
import java.time.LocalDateTime;

@Setter
@Getter
@Component
public class ScheduledReportGenerator implements ReportVisitor {

    @Override
    public String getReportName() {
        return "ScheduledReport";
    }

    @Override
    public Mono<Report> visit(CpuLoadMonitoringService cpuLoadMonitoringService, ReportRequestDto reportRequestDto) {
        return defaultVisiting(cpuLoadMonitoringService, "CPU Load Monitoring",
                reportRequestDto.getScheduleStartTime(), reportRequestDto.getScheduleEndTime());
    }

    @Override
    public Mono<Report> visit(KeyLoggerMonitoringService keyLoggerMonitoringService, ReportRequestDto reportRequestDto) {

        return defaultVisiting(keyLoggerMonitoringService, "Key Logger Monitoring",
                reportRequestDto.getScheduleStartTime(), reportRequestDto.getScheduleEndTime());
    }

    @Override
    public Mono<Report> visit(MemoryMonitoringService memoryMonitoringService, ReportRequestDto reportRequestDto) {

        return defaultVisiting(memoryMonitoringService, "Memory Monitoring",
                reportRequestDto.getScheduleStartTime(), reportRequestDto.getScheduleEndTime());
    }

    @Override
    public Mono<Report> visit(MouseTrackerMonitoringService mouseTrackerMonitoringService, ReportRequestDto reportRequestDto) {

        return defaultVisiting(mouseTrackerMonitoringService, "Mouse Tracker Monitoring",
                reportRequestDto.getScheduleStartTime(), reportRequestDto.getScheduleEndTime());
    }

    @Override
    public Mono<Report> visit(WindowsMonitoringService windowsMonitoringService, ReportRequestDto reportRequestDto) {

        return defaultVisiting(windowsMonitoringService, "Windows Monitoring",
                reportRequestDto.getScheduleStartTime(), reportRequestDto.getScheduleEndTime());
    }

    @Override
    public Mono<Report> visit(TestMonitoring testMonitoring, ReportRequestDto reportRequestDto) {
        return defaultVisiting(testMonitoring, "Test Monitoring",
                reportRequestDto.getScheduleStartTime(), reportRequestDto.getScheduleEndTime());
    }

    private Mono<Report> defaultVisiting(Monitoring monitoring, String serviceName,
                                         LocalDateTime startTime,
                                         LocalDateTime endTime) {

        if (startTime == null || endTime == null) {
            throw new IllegalStateException("Start and end times must be set before generating a report");
        }
        else if (startTime.isAfter(endTime)) {
            throw new IllegalStateException("Start time must be before end time");
        }


        Duration delay = Duration.between(startTime, endTime);
        Mono<Long> delayMono = Mono.delay(delay);

        Flux<MonitoringPoint> dataFlux = monitoring.startMonitoring(true)
                .takeWhile(point -> LocalDateTime.now().isBefore(endTime));

        return delayMono.thenMany(dataFlux).collectList().map(data -> {
            Duration duration = Duration.between(startTime, endTime);

            String title = serviceName + " Report - from " + startTime + " to " + endTime;

            Report report = new Report(title, duration, getReportName());
            report.setData(data);

            return report;
        });
    }
}
