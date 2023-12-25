package com.example.activitymonitor.monitoring.application.commands;

import com.example.activitymonitor.monitoring.application.service.MemoryMonitoringService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoryMonitoringCommand extends StartMonitoringCommand {

    public MemoryMonitoringCommand(MemoryMonitoringService memoryMonitoringService, boolean isMonitoringStarted) {
        super(memoryMonitoringService, isMonitoringStarted);
    }

    @Override
    public void execute() {
        monitoring.startMonitoring(isMonitoringStarted);
    }
}

