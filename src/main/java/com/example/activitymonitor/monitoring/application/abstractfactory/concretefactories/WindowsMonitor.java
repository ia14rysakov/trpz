package com.example.activitymonitor.monitoring.application.abstractfactory.concretefactories;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.abstractfactory.AbstractMonitor;
import com.example.activitymonitor.monitoring.application.service.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class WindowsMonitor extends AbstractMonitor {

    private String osType = "Windows";

    @Override
    public Monitoring setCpuLoadMonitor() {
        return new CpuLoadMonitoringService();
    }

    @Override
    public Monitoring setKeyLoggerMonitor() {
        return new KeyLoggerMonitoringService();
    }

    @Override
    public Monitoring setMemoryMonitor() {
        return new MemoryMonitoringService();
    }

    @Override
    public Monitoring setMouseTrackerMonitor() {
        return new MouseTrackerMonitoringService();
    }

    @Override
    public Monitoring setWindowsMonitor() {
        return new WindowsMonitoringService();
    }

    @Override
    public Monitoring setTestMonitor() {
        return new TestMonitoring();
    }
}

