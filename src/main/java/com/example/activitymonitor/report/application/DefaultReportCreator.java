package com.example.activitymonitor.report.application;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.report.domain.Report;

import java.time.LocalDateTime;

public class DefaultReportCreator implements ReportCreator {
    public Monitoring monitoring;

    public DefaultReportCreator(Monitoring monitoring) {
        this.monitoring = monitoring;
    }

    @Override
    public Report generateScheduledReport(LocalDateTime start, LocalDateTime end) {
        return monitoring.generateScheduledReport(start, end);
    }

    @Override
    public Report generateReportByTime(LocalDateTime end) {
        return monitoring.generateReportByTime(end);
    }

    @Override
    public Report startReporting() {
        return monitoring.startReporting();
    }

    @Override
    public Report stopReporting() {
        return monitoring.stopReporting();

    }
}

