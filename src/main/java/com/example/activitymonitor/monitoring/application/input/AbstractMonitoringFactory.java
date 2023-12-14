package com.example.activitymonitor.monitoring.application.input;

import com.example.activitymonitor.monitoring.domain.MonitoringRequest;

public interface AbstractMonitoringFactory {
    void startMonitoring(MonitoringRequest monitoringRequest);
}
