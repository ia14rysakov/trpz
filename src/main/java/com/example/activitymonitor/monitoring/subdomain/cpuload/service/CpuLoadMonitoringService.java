package com.example.activitymonitor.monitoring.subdomain.cpuload.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.Visitor;

public class CpuLoadMonitoringService implements Monitoring {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void startMonitoring() {
        // TODO Auto-generated method stub

    }
}
