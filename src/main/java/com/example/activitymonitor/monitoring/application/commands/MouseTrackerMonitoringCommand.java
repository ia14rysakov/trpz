package com.example.activitymonitor.monitoring.application.commands;

import com.example.activitymonitor.monitoring.application.service.MouseTrackerMonitoringService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MouseTrackerMonitoringCommand implements StartMonitoringCommand {

    private MouseTrackerMonitoringService mouseTrackerMonitoringService;
    private boolean isMonitoringStarted;

    public MouseTrackerMonitoringCommand(MouseTrackerMonitoringService mouseTrackerMonitoringService, boolean isMonitoringStarted) {
        this.mouseTrackerMonitoringService = mouseTrackerMonitoringService;
        this.isMonitoringStarted = isMonitoringStarted;
    }

    @Override
    public void execute() {
        mouseTrackerMonitoringService.startMonitoring(isMonitoringStarted);
    }
}

