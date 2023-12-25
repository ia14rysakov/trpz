package com.example.activitymonitor.monitoring.domain.points;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import lombok.Getter;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.stream.Stream;

@Getter
public class CPUMonitoringPoint implements MonitoringPoint {
    private double cpuUsage;

    public CPUMonitoringPoint(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

}
