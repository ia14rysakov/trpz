package com.example.activitymonitor.monitoring.application;

public interface Monitoring {
    void accept(Visitor visitor);

    void startMonitoring(boolean isMonitoringStarted);
}
