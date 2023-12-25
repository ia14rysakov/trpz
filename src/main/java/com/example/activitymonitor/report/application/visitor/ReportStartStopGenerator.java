package com.example.activitymonitor.report.application.visitor;

import com.example.activitymonitor.monitoring.application.service.*;
import com.example.activitymonitor.report.domain.Report;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

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
