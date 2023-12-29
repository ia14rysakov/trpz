package com.example.activitymonitor.monitoring.domain.points;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KeyLoggerMonitoringPoint implements MonitoringPoint {
    private String lastKeyPressed;

    public KeyLoggerMonitoringPoint(String lastKeyPressed) {
        this.lastKeyPressed = lastKeyPressed;
    }

}
