package com.example.activitymonitor.monitoring.application.input;

import com.example.activitymonitor.monitoring.domain.MonitoringRequest;

public interface Invoker {
    void invoke(MonitoringRequest monitoringRequest);
}
