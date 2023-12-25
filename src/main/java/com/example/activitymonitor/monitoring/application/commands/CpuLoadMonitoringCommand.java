package com.example.activitymonitor.monitoring.application.commands;

import com.example.activitymonitor.monitoring.application.service.CpuLoadMonitoringService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CpuLoadMonitoringCommand implements StartMonitoringCommand {

    private CpuLoadMonitoringService cpuLoadMonitoringService;

    private boolean isMonitoringStarted;

    public CpuLoadMonitoringCommand(CpuLoadMonitoringService cpuLoadMonitoringService, boolean isMonitoringStarted) {
        this.cpuLoadMonitoringService = cpuLoadMonitoringService;
        this.isMonitoringStarted = isMonitoringStarted;
    }
    @Override
    public void execute() {
        cpuLoadMonitoringService.startMonitoring(isMonitoringStarted);
    }
}

