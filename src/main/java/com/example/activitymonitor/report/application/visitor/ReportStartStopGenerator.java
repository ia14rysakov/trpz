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
import java.util.ArrayList;
import java.util.Iterator;
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
    public Report visit(CpuLoadMonitoringService cpuLoadMonitoringService) {
        return defaultVisiting(cpuLoadMonitoringService, "CPU Load Monitoring");
    }

    @Override
    public Report visit(KeyLoggerMonitoringService keyLoggerMonitoringService) {
        return defaultVisiting(keyLoggerMonitoringService, "Key Logger Monitoring");
    }

    @Override
    public Report visit(MemoryMonitoringService memoryMonitoringService) {
        return defaultVisiting(memoryMonitoringService, "Memory Monitoring");
    }

    @Override
    public Report visit(MouseTrackerMonitoringService mouseTrackerMonitoringService) {
        return defaultVisiting(mouseTrackerMonitoringService, "Mouse Tracker Monitoring");
    }

    @Override
    public Report visit(WindowsMonitoringService windowsMonitoringService) {
        return defaultVisiting(windowsMonitoringService, "Windows Monitoring");
    }

    private Report defaultVisiting(Monitoring monitoring, String serviceName) {
        LocalDateTime startTime = LocalDateTime.now();

        List<MonitoringPoint> data = new ArrayList<>();
        try {
            Iterator<MonitoringPoint> iterator = monitoring.startMonitoring(true).iterator();
            while (isMonitoringStarted && iterator.hasNext()) {
                data.add(iterator.next());
            }
        } catch (Exception e) {
            //TODO log exception
        }

        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);

        String title = serviceName + " Report - from " + startTime + " to " + endTime;

        Report report = new Report(title, duration, getReportName());
        report.setData(data);

        return report;
    }
}
