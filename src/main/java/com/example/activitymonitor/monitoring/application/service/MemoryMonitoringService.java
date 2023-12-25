package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.points.MemoryMonitoringPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class MemoryMonitoringService implements Monitoring {

    @Override
    public String getMonitoringName() {
        return "MemoryMonitoring";
    }

    @Override
    public Report accept(ReportVisitor reportVisitor) {
        return reportVisitor.visit(this);
    }

    @Override
    public Stream<MonitoringPoint> startMonitoring(boolean isMonitoringStarted) {
        return Stream.generate(() -> {
            while (isMonitoringStarted) {
                Runtime runtime = Runtime.getRuntime();
                double memoryUsage = (double) (runtime.totalMemory() - runtime.freeMemory()) / runtime.totalMemory();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return new MemoryMonitoringPoint(memoryUsage);
            }
            return null;
        });
    }
}
