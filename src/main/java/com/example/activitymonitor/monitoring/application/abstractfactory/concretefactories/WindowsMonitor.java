package com.example.activitymonitor.monitoring.application.abstractfactory.concretefactories;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.abstractfactory.AbstractMonitor;
import com.example.activitymonitor.monitoring.application.commands.*;
import com.example.activitymonitor.monitoring.application.commands.StartMonitoringCommand;
import com.example.activitymonitor.monitoring.application.service.*;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

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

