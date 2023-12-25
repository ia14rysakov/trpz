package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.points.MouseTrackerMonitoringPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;
import reactor.core.publisher.Flux;

import java.awt.*;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class MouseTrackerMonitoringService implements Monitoring {

    @Override
    public String getMonitoringName() {
        return "MouseTracker";
    }

    @Override
    public Report accept(ReportVisitor reportVisitor) {
        return reportVisitor.visit(this);
    }

    @Override
    public Flux<MonitoringPoint> startMonitoring(boolean isMonitoringStarted) {
        return Flux.generate(sink -> {
            if (isMonitoringStarted) {
                Point position = MouseInfo.getPointerInfo().getLocation();
                sink.next(new MouseTrackerMonitoringPoint(position));
            } else {
                sink.complete();
            }
        }).delayElements(Duration.ofSeconds(1)).cast(MonitoringPoint.class);
    }
}
