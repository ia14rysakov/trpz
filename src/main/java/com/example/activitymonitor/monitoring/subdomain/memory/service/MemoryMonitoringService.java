package com.example.activitymonitor.monitoring.subdomain.memory.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.Visitor;

public class MemoryMonitoringService implements Monitoring {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void startMonitoring() {

    }
}
