package com.example.activitymonitor.monitoring.domain.points;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoryMonitoringPoint implements MonitoringPoint {
    private double memoryUsage;

    private LocalDateTime timestamp = LocalDateTime.now();

    public MemoryMonitoringPoint(double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

}
