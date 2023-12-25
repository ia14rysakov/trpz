package com.example.activitymonitor.report.application.visitor;

import com.example.activitymonitor.monitoring.application.service.*;
import com.example.activitymonitor.report.domain.Report;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Setter
@Getter
@Component
public class ScheduledReportGenerator implements ReportVisitor {

    private LocalDateTime start;
    private LocalDateTime end;

    @Override
    public String getReportName() {
        return "ScheduledReport";
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
