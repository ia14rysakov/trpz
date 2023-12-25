package com.example.activitymonitor.monitoring.domain.points;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import lombok.Getter;

import java.util.List;

@Getter
public class WindowsMonitoringPoint implements MonitoringPoint {
    private List<WindowsPoint> windowsPoints;

    public WindowsMonitoringPoint(List<WindowsPoint> windowsPoints) {
        this.windowsPoints = windowsPoints;
    }

}
