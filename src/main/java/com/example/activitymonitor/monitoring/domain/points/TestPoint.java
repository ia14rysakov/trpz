package com.example.activitymonitor.monitoring.domain.points;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import lombok.Getter;
import lombok.ToString;

@Getter
public class TestPoint implements MonitoringPoint {
    private String test;

    private String timestamp = java.time.LocalDateTime.now().toString();

    public TestPoint(String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }

    @Override
    public String toString() {
        return "TestPoint{" + "test=" + test + ", timestamp=" + timestamp + '}' + "\n";
    }
}
