package com.example.activitymonitor.monitoring.application.commands;

import com.example.activitymonitor.monitoring.application.output.StartMonitoringCommand;
import com.example.activitymonitor.monitoring.application.service.WindowsMonitoringService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WindowsMonitoringCommand implements StartMonitoringCommand {

    private WindowsMonitoringService windowsMonitoringService;
    private boolean isMonitoringStarted;

    public WindowsMonitoringCommand(WindowsMonitoringService windowsMonitoringService, boolean isMonitoringStarted) {
        this.windowsMonitoringService = windowsMonitoringService;
        this.isMonitoringStarted = isMonitoringStarted;
    }

    @Override
    public void execute() {
        windowsMonitoringService.startMonitoring(isMonitoringStarted);
    }
}

