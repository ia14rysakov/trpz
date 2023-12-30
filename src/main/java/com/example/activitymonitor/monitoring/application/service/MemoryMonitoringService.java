package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.MonitoringType;
import com.example.activitymonitor.monitoring.domain.points.MemoryMonitoringPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.infrastructure.rest.dto.ReportRequestDto;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class MemoryMonitoringService implements Monitoring {

    private final SystemInfo systemInfo = new SystemInfo();

    @Override
    public String getMonitoringName() {
        return MonitoringType.MEMORY_USAGE.name();
    }

    @Override
    public Mono<Report> accept(ReportVisitor reportVisitor, ReportRequestDto reportRequestDto) {
        return reportVisitor.visit(this, reportRequestDto);
    }

    @Override
    public Flux<MonitoringPoint> startMonitoring(boolean isMonitoringStarted) {
        return Flux.generate(sink -> {
            if (isMonitoringStarted) {
                GlobalMemory memory = systemInfo.getHardware().getMemory();
                double memoryUsage = (double) (memory.getTotal() - memory.getAvailable()) / memory.getTotal() * 100;
                sink.next(new MemoryMonitoringPoint(memoryUsage));
            } else {
                sink.complete();
            }
        }).delayElements(Duration.ofSeconds(1)).cast(MonitoringPoint.class);
    }
}
