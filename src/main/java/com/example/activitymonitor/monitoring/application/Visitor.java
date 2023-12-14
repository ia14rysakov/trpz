package com.example.activitymonitor.monitoring.application;

import com.example.activitymonitor.monitoring.subdomain.cpuload.service.CpuLoadMonitoringService;
import com.example.activitymonitor.monitoring.subdomain.keylogger.service.KeyLoggerMonitoringService;
import com.example.activitymonitor.monitoring.subdomain.memory.service.MemoryMonitoringService;
import com.example.activitymonitor.monitoring.subdomain.mousetracker.service.MouseTrackerMonitoringService;
import com.example.activitymonitor.monitoring.subdomain.windows.service.WindowsMonitoringService;

public interface Visitor {
    void visit(CpuLoadMonitoringService cpuLoadMonitoringService);

    void visit(KeyLoggerMonitoringService keyLoggerMonitoringService);

    void visit(MemoryMonitoringService memoryMonitoringService);

    void visit(MouseTrackerMonitoringService mouseTrackerMonitoringService);

    void visit(WindowsMonitoringService windowsMonitoringService);


}
