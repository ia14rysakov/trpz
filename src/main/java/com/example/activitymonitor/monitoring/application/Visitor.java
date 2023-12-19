package com.example.activitymonitor.monitoring.application;

import com.example.activitymonitor.monitoring.application.service.*;

public interface Visitor {
    void visit(CpuLoadMonitoringService cpuLoadMonitoringService);

    void visit(KeyLoggerMonitoringService keyLoggerMonitoringService);

    void visit(MemoryMonitoringService memoryMonitoringService);

    void visit(MouseTrackerMonitoringService mouseTrackerMonitoringService);

    void visit(WindowsMonitoringService windowsMonitoringService);


}
