package com.example.activitymonitor.monitoring.application.visitor;

import com.example.activitymonitor.monitoring.application.service.*;
import com.example.activitymonitor.report.domain.Report;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ScheduledReportGenerator implements ReportVisitor {

    private LocalDateTime start;
    private LocalDateTime end;

    public ScheduledReportGenerator(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }


    @Override
    public Report visit(CpuLoadMonitoringService cpuLoadMonitoringService) {
        return null;
    }

    @Override
    public Report visit(KeyLoggerMonitoringService keyLoggerMonitoringService) {
        return null;
    }

    @Override
    public Report visit(MemoryMonitoringService memoryMonitoringService) {
        return null;
    }

    @Override
    public Report visit(MouseTrackerMonitoringService mouseTrackerMonitoringService) {
        return null;
    }

    @Override
    public Report visit(WindowsMonitoringService windowsMonitoringService) {
        return null;
    }
}
