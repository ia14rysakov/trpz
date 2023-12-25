package com.example.activitymonitor.monitoring.application.commands;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;

import java.util.stream.Stream;

public abstract class StartMonitoringCommand {

    protected Monitoring monitoring;
    protected boolean isMonitoringStarted;

    public StartMonitoringCommand(Monitoring monitoring, boolean isMonitoringStarted) {
        this.monitoring = monitoring;
        this.isMonitoringStarted = isMonitoringStarted;
    }
    abstract void execute();

}

