package com.example.activitymonitor.monitoring.application.commands;

import com.example.activitymonitor.monitoring.application.service.MouseTrackerMonitoringService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MouseTrackerMonitoringCommand extends StartMonitoringCommand {
    public MouseTrackerMonitoringCommand(MouseTrackerMonitoringService mouseTrackerMonitoringService, boolean isMonitoringStarted) {
        super(mouseTrackerMonitoringService, isMonitoringStarted);
    }

    @Override
    public void execute() {
        monitoring.startMonitoring(isMonitoringStarted);
    }
}

