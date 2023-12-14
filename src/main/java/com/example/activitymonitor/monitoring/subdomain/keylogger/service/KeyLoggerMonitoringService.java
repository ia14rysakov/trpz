package com.example.activitymonitor.monitoring.subdomain.keylogger.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.Visitor;

public class KeyLoggerMonitoringService implements Monitoring {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void startMonitoring() {
        //TODO
    }
}
