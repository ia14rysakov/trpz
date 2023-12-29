package com.example.activitymonitor.monitoring.domain.points;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import lombok.Getter;
import lombok.ToString;

@Getter
public class MouseTrackerMonitoringPoint implements MonitoringPoint {

    private String x;
    private String y;

    private String timestamp = java.time.LocalDateTime.now().toString();

    public MouseTrackerMonitoringPoint(String x, String y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "MouseTrackerMonitoringPoint{" + "x=" + x + ", y=" + y + ", timestamp=" + timestamp + '}' + "\n";
    }
}
