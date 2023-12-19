package com.example.activitymonitor.monitoring.application.commands;

import com.example.activitymonitor.monitoring.application.output.StartMonitoringCommand;
import com.example.activitymonitor.monitoring.application.service.KeyLoggerMonitoringService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeyLoggerMonitoringCommand implements StartMonitoringCommand {

    private KeyLoggerMonitoringService keyLoggerMonitoringService;
    private boolean isMonitoringStarted;

    public KeyLoggerMonitoringCommand(KeyLoggerMonitoringService keyLoggerMonitoringService, boolean isMonitoringStarted) {
        this.keyLoggerMonitoringService = keyLoggerMonitoringService;
        this.isMonitoringStarted = isMonitoringStarted;
    }
    @Override
    public void execute() {
        keyLoggerMonitoringService.startMonitoring(isMonitoringStarted);
    }
}

