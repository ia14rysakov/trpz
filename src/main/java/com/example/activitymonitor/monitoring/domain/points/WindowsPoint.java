package com.example.activitymonitor.monitoring.domain.points;

import lombok.Getter;

@Getter
public class WindowsPoint {
    private String windowName;
    private String windowSize;
    private double cpuUsage;
    private double memoryUsage;

    public WindowsPoint(String windowName, String windowSize, double cpuUsage, double memoryUsage) {
        this.windowName = windowName;
        this.windowSize = windowSize;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
    }
}
