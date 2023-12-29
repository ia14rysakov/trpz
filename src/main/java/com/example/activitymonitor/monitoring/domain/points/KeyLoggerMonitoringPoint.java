package com.example.activitymonitor.monitoring.domain.points;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
public class KeyLoggerMonitoringPoint implements MonitoringPoint {
    private String lastKeyPressed;
    private String timestamp = LocalDateTime.now().toString();

    public KeyLoggerMonitoringPoint(String lastKeyPressed) {
        this.lastKeyPressed = lastKeyPressed;
    }

    @Override
    public String toString() {
        return "Key " + lastKeyPressed + " was pressed at " + timestamp + "\n";
    }
}
