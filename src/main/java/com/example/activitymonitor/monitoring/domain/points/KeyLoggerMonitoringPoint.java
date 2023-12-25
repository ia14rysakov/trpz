package com.example.activitymonitor.monitoring.domain.points;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;

public class KeyLoggerMonitoringPoint implements MonitoringPoint {
    private String lastKeyPressed;

    public KeyLoggerMonitoringPoint(String lastKeyPressed) {
        this.lastKeyPressed = lastKeyPressed;
    }

    public String getLastKeyPressed() {
        return lastKeyPressed;
    }
}
