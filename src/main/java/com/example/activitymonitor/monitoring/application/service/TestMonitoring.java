package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.points.TestPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class TestMonitoring implements Monitoring {
    @Override
    public String getMonitoringName() {
        return "test";
    }

    @Override
    public Report accept(ReportVisitor reportVisitor) {
        return null;
    }

    @Override
    public Stream<MonitoringPoint> startMonitoring(boolean isMonitoringStarted) {
        return Stream.generate(() -> {
            while (isMonitoringStarted) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return new TestPoint("test");
            }
            return null;
        });
    }
}
