package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.points.TestPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class TestMonitoring implements Monitoring {
    @Override
    public String getMonitoringName() {
        return "test";
    }

    @Override
    public Mono<Report> accept(ReportVisitor reportVisitor) {
        return reportVisitor.visit(this);
    }

    @Override
    public Flux<MonitoringPoint> startMonitoring(boolean isMonitoringStarted) {
        return Flux.generate(sink -> {
            if (isMonitoringStarted) {
                sink.next(new TestPoint("test" + System.currentTimeMillis()/1000 ));
            } else {
                sink.complete();
            }
        }).delayElements(Duration.ofSeconds(1)).cast(MonitoringPoint.class);
    }
}
