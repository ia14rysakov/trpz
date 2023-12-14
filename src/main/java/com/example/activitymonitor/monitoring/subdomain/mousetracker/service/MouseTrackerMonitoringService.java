package com.example.activitymonitor.monitoring.subdomain.mousetracker.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.Visitor;

public class MouseTrackerMonitoringService implements Monitoring {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void startMonitoring() {

    }
}
