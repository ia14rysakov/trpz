package com.example.activitymonitor.report.application.visitor;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.service.*;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.report.domain.Report;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Component
public class ReportByTimeGenerator implements ReportVisitor {

    private LocalDateTime dueToTime = null;

    @Override
    public String getReportName() {
        return "ReportByTime";
    }

    @Override
    public Report visit(CpuLoadMonitoringService cpuLoadMonitoringService) {
        return commonVisitor(cpuLoadMonitoringService, "CPU Load Monitoring");
    }

    @Override
    public Report visit(KeyLoggerMonitoringService keyLoggerMonitoringService) {
        return commonVisitor(keyLoggerMonitoringService, "Key Logger Monitoring");
    }

    @Override
    public Report visit(MemoryMonitoringService memoryMonitoringService) {
        return commonVisitor(memoryMonitoringService, "Memory Monitoring");
    }

    @Override
    public Report visit(MouseTrackerMonitoringService mouseTrackerMonitoringService) {

        return commonVisitor(mouseTrackerMonitoringService, "Mouse Tracker Monitoring");
    }

    @Override
    public Report visit(WindowsMonitoringService windowsMonitoringService) {

        return commonVisitor(windowsMonitoringService, "Windows Monitoring");
    }

    private Report commonVisitor(Monitoring monitoringService, String serviceName) {
        if (dueToTime == null) {
            throw new IllegalStateException("dueToTime must be set before generating a report");
        }

        LocalDateTime startTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, dueToTime);

        List<MonitoringPoint> data = monitoringService.startMonitoring(true)
                .takeWhile(point -> LocalDateTime.now().isBefore(dueToTime))
                .collect(Collectors.toList());

        String title = serviceName + " Report - from " + startTime + " to " + dueToTime;

        Report report = new Report(title, duration, getReportName());
        report.setData(data);

        return report;
    }
}
