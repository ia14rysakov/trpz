package com.example.activitymonitor.monitoring.domain.points;

import lombok.Getter;
import lombok.ToString;

@Getter
public class WindowsPoint {
    private String windowName;
    private String windowSize;

    public WindowsPoint(String windowName, String windowSize) {
        this.windowName = windowName;
        this.windowSize = windowSize;
    }

    @Override
    public String toString() {
        return "WindowsPoint{" + "windowName=" + windowName + ", windowSize=" + windowSize + '}';
    }
}
