package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.points.MouseTrackerMonitoringPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;

import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class MouseTrackerMonitoringService implements Monitoring {

    @Override
    public String getMonitoringName() {
        return "MouseTracker";
    }

    @Override
    public Report accept(ReportVisitor reportVisitor) {
        return reportVisitor.visit(this);
    }

    @Override
    public Stream<MonitoringPoint> startMonitoring(boolean isMonitoringStarted) {
        return Stream.generate(() -> {
            while (isMonitoringStarted) {
                Point position = MouseInfo.getPointerInfo().getLocation();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return new MouseTrackerMonitoringPoint(position);
            }
            return null;
        });
    }
}
