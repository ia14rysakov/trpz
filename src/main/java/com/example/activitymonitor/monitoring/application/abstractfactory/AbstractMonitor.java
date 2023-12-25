package com.example.activitymonitor.monitoring.application.abstractfactory;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.report.infrastructure.rest.dto.ReportRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractMonitor {

    private String osType;

    abstract public Monitoring setCpuLoadMonitor();

    abstract public Monitoring setKeyLoggerMonitor();

    abstract public Monitoring setMemoryMonitor();

    abstract public Monitoring setMouseTrackerMonitor();

    abstract public Monitoring setWindowsMonitor();

    public void startMonitoring(String monitoringType) {
        switch (monitoringType) {
            case "cpuLoad":
                setCpuLoadMonitor().startMonitoring(true);
                break;
            case "keyLogger":
                setKeyLoggerMonitor().startMonitoring(true);
                break;
            case "memory":
                setMemoryMonitor().startMonitoring(true);
                break;
            case "mouseTracker":
                setMouseTrackerMonitor().startMonitoring(true);
                break;
            case "windows":
                setWindowsMonitor().startMonitoring(true);
                break;
            default:
                throw new IllegalArgumentException("Invalid monitoring type: " + monitoringType);
        }
    }

    public Monitoring getConcreteMonitoring(ReportRequestDto report) {
        return switch (report.getMonitoringType()) {
            case "cpuLoad" -> setCpuLoadMonitor();
            case "keyLogger" -> setKeyLoggerMonitor();
            case "memory" -> setMemoryMonitor();
            case "mouseTracker" -> setMouseTrackerMonitor();
            case "windows" -> setWindowsMonitor();
            default -> throw new IllegalArgumentException("Invalid monitoring type: " + report.getMonitoringType());
        };
    }
}

