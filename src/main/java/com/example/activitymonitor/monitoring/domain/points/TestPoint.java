package com.example.activitymonitor.monitoring.domain.points;

import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TestPoint implements MonitoringPoint {
    private String test;

    public TestPoint(String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }
}
