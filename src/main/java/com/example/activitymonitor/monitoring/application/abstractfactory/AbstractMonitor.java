package com.example.activitymonitor.monitoring.application.abstractfactory;

import com.example.activitymonitor.monitoring.application.Monitoring;

public abstract class AbstractMonitor {

    public String osType;

    public AbstractMonitor(String osType) {
        this.osType = osType;
    }

    abstract public Monitoring setCpuLoadMonitor();

    abstract public Monitoring setKeyLoggerMonitor();

    abstract public Monitoring setMemoryMonitor();

    abstract public Monitoring setMouseTrackerMonitor();

    abstract public Monitoring setWindowsMonitor();
}

