package com.example.activitymonitor.monitoring.application.abstractfactory;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.MonitoringType;
import com.example.activitymonitor.report.infrastructure.rest.dto.ReportRequestDto;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Flux;

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
        MonitoringType monitoringTypeEnum = MonitoringType.valueOf(monitoringType.toUpperCase());
        return switch (monitoringTypeEnum) {
            case CPU_USAGE -> setCpuLoadMonitor().startMonitoring(true);
            case KEYBOARD_ACTIVITY -> setKeyLoggerMonitor().startMonitoring(true);
            case MEMORY_USAGE -> setMemoryMonitor().startMonitoring(true);
            case MOUSE_ACTIVITY -> setMouseTrackerMonitor().startMonitoring(true);
            case WINDOWS -> setWindowsMonitor().startMonitoring(true);
            case TEST -> setTestMonitor().startMonitoring(true);
        };
    }

    public Monitoring getConcreteMonitoring(ReportRequestDto report) {
        return switch (MonitoringType.valueOf(report.getMonitoringType())) {
            case CPU_USAGE -> setCpuLoadMonitor();
            case KEYBOARD_ACTIVITY -> setKeyLoggerMonitor();
            case MEMORY_USAGE -> setMemoryMonitor();
            case MOUSE_ACTIVITY -> setMouseTrackerMonitor();
            case WINDOWS -> setWindowsMonitor();
            case TEST -> setTestMonitor();
        };
    }
}

