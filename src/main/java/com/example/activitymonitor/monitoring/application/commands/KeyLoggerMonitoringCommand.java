package com.example.activitymonitor.monitoring.application.commands;

import com.example.activitymonitor.monitoring.application.service.KeyLoggerMonitoringService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeyLoggerMonitoringCommand extends StartMonitoringCommand {

    public KeyLoggerMonitoringCommand(KeyLoggerMonitoringService keyLoggerMonitoringService, boolean isMonitoringStarted) {
        super(keyLoggerMonitoringService, isMonitoringStarted);
    }
    @Override
    public void execute() {
        monitoring.startMonitoring(isMonitoringStarted);
    }
}

