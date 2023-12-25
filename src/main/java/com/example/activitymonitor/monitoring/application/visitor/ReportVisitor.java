package com.example.activitymonitor.monitoring.application.visitor;

import com.example.activitymonitor.monitoring.application.service.*;
import com.example.activitymonitor.report.domain.Report;

public interface ReportVisitor {
    Report visit(CpuLoadMonitoringService cpuLoadMonitoringService);

    Report visit(KeyLoggerMonitoringService keyLoggerMonitoringService);

    Report visit(MemoryMonitoringService memoryMonitoringService);

    Report visit(MouseTrackerMonitoringService mouseTrackerMonitoringService);

    Report visit(WindowsMonitoringService windowsMonitoringService);


}
