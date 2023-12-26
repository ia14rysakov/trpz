package com.example.activitymonitor.monitoring.domain.points;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import lombok.Getter;

@Getter
public class MouseTrackerMonitoringPoint implements MonitoringPoint {

    private String x;
    private String y;

    public MouseTrackerMonitoringPoint(String x, String y) {
        this.x = x;
        this.y = y;
    }
}
