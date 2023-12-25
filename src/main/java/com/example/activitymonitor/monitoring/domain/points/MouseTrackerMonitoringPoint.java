package com.example.activitymonitor.monitoring.domain.points;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;

import java.awt.*;

public class MouseTrackerMonitoringPoint implements MonitoringPoint {
    private Point position;

    public MouseTrackerMonitoringPoint(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }
}
