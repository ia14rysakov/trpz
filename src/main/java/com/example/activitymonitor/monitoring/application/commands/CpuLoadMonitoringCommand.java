package com.example.activitymonitor.monitoring.application.commands;

import com.example.activitymonitor.monitoring.application.service.CpuLoadMonitoringService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CpuLoadMonitoringCommand extends StartMonitoringCommand {

    public CpuLoadMonitoringCommand(CpuLoadMonitoringService cpuLoadMonitoringService, boolean isMonitoringStarted) {
        super(cpuLoadMonitoringService, isMonitoringStarted);
    }
    @Override
    public void execute() {
        monitoring.startMonitoring(isMonitoringStarted);
    }
}

