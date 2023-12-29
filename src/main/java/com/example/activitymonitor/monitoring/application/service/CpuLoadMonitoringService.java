package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.points.CPUMonitoringPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.infrastructure.rest.dto.ReportRequestDto;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.logging.Logger;

public class CpuLoadMonitoringService implements Monitoring {

    private Logger logger = Logger.getLogger(CpuLoadMonitoringService.class.getName());

    @Override
    public String getMonitoringName() {
        return "CPUMonitoring";
    }

    @Override
    public Mono<Report> accept(ReportVisitor reportVisitor, ReportRequestDto reportRequestDto) {
        return reportVisitor.visit(this, reportRequestDto);
    }


    @Override
    public Flux<MonitoringPoint> startMonitoring(boolean isMonitoringStarted) {
        SystemInfo si = new SystemInfo();
        CentralProcessor processor = si.getHardware().getProcessor();
        return Flux.generate(sink -> {
            if (isMonitoringStarted) {
                double cpuLoad = processor.getSystemCpuLoad(1000) * 1000;
                logger.info("CPU load: " + cpuLoad);
                sink.next(new CPUMonitoringPoint(cpuLoad));
            } else {
                sink.complete();
            }
        }).delayElements(Duration.ofSeconds(1)).cast(MonitoringPoint.class);
    }
}

