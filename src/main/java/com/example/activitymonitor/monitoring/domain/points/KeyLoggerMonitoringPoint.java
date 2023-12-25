package com.example.activitymonitor.monitoring.domain.points;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import lombok.Getter;

@Getter
public class KeyLoggerMonitoringPoint implements MonitoringPoint {
    private String lastKeyPressed;

    public KeyLoggerMonitoringPoint(String lastKeyPressed) {
        this.lastKeyPressed = lastKeyPressed;
    }

}
