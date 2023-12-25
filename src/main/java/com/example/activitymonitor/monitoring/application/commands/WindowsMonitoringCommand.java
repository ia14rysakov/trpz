package com.example.activitymonitor.monitoring.application.commands;

import com.example.activitymonitor.monitoring.application.service.WindowsMonitoringService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WindowsMonitoringCommand extends StartMonitoringCommand {

    public WindowsMonitoringCommand(WindowsMonitoringService windowsMonitoringService, boolean isMonitoringStarted) {
        super(windowsMonitoringService, isMonitoringStarted);
    }

    @Override
    public void execute() {
        monitoring.startMonitoring(isMonitoringStarted);
    }
}

