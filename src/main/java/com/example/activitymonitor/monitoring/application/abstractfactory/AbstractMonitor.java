package com.example.activitymonitor.monitoring.application.abstractfactory;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.report.infrastructure.rest.dto.ReportRequestDto;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

@Getter
@Setter
public abstract class AbstractMonitor {

    private String osType;

    abstract public Monitoring setCpuLoadMonitor();

    abstract public Monitoring setKeyLoggerMonitor();

    abstract public Monitoring setMemoryMonitor();

    abstract public Monitoring setMouseTrackerMonitor();

    abstract public Monitoring setWindowsMonitor();

    abstract public Monitoring setTestMonitor();

    public Flux<MonitoringPoint> startMonitoring(String monitoringType) {
        return switch (monitoringType) {
            case "cpuLoad" -> setCpuLoadMonitor().startMonitoring(true);
            case "keyLogger" -> setKeyLoggerMonitor().startMonitoring(true);
            case "memory" -> setMemoryMonitor().startMonitoring(true);
            case "mouseTracker" -> setMouseTrackerMonitor().startMonitoring(true);
            case "windows" -> setWindowsMonitor().startMonitoring(true);
            case "test" -> setTestMonitor().startMonitoring(true);
            default -> throw new IllegalArgumentException("Invalid monitoring type: " + monitoringType);
        };
    }

    public Monitoring getConcreteMonitoring(ReportRequestDto report) {
        return switch (report.getMonitoringType()) {
            case "cpuLoad" -> setCpuLoadMonitor();
            case "keyLogger" -> setKeyLoggerMonitor();
            case "memory" -> setMemoryMonitor();
            case "mouseTracker" -> setMouseTrackerMonitor();
            case "windows" -> setWindowsMonitor();
            case "test" -> setTestMonitor();
            default -> throw new IllegalArgumentException("Invalid monitoring type: " + report.getMonitoringType());
        };
    }
}

