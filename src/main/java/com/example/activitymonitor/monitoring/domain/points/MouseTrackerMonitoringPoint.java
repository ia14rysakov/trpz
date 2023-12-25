package com.example.activitymonitor.monitoring.domain.points;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import lombok.Getter;

import java.awt.*;

@Getter
public class MouseTrackerMonitoringPoint implements MonitoringPoint {
    private Point position;

    public MouseTrackerMonitoringPoint(Point position) {
        this.position = position;
    }

}
