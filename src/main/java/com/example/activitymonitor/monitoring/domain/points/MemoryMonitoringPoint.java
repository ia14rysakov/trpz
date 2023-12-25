package com.example.activitymonitor.monitoring.domain.points;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;

public class MemoryMonitoringPoint implements MonitoringPoint {
    private double memoryUsage;

    public MemoryMonitoringPoint(double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public double getMemoryUsage() {
        return memoryUsage;
    }
}
