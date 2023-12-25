package com.example.activitymonitor.monitoring.domain.points;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import lombok.Getter;

@Getter
public class MemoryMonitoringPoint implements MonitoringPoint {
    private double memoryUsage;

    public MemoryMonitoringPoint(double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

}
