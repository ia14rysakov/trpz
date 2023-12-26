package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.points.MemoryMonitoringPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class MemoryMonitoringService implements Monitoring {

    @Override
    public String getMonitoringName() {
        return "MemoryMonitoring";
    }

    @Override
    public Mono<Report> accept(ReportVisitor reportVisitor) {
        return reportVisitor.visit(this);
    }

    @Override
    public Flux<MonitoringPoint> startMonitoring(boolean isMonitoringStarted) {
        return Flux.generate(sink -> {
            if (isMonitoringStarted) {
                Runtime runtime = Runtime.getRuntime();
                double memoryUsage = (double)
                        ((runtime.totalMemory() - runtime.freeMemory()) / runtime.totalMemory()) * 100;
                sink.next(new MemoryMonitoringPoint(memoryUsage));
            } else {
                sink.complete();
            }
        }).delayElements(Duration.ofSeconds(1)).cast(MonitoringPoint.class);
    }
}
