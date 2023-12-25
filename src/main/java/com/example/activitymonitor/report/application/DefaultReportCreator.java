package com.example.activitymonitor.report.application;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.visitor.ReportByTimeGenerator;
import com.example.activitymonitor.monitoring.application.visitor.ReportStartStopGenerator;
import com.example.activitymonitor.monitoring.application.visitor.ScheduledReportGenerator;
import com.example.activitymonitor.report.domain.Report;

import java.time.LocalDateTime;

public class DefaultReportCreator implements ReportCreator {
    public Monitoring monitoring;

    public DefaultReportCreator(Monitoring monitoring) {
        this.monitoring = monitoring;
    }

    @Override
    public Report generateScheduledReport(LocalDateTime start, LocalDateTime end) {
        return monitoring.accept(new ScheduledReportGenerator(start, end));
    }

    @Override
    public Report generateReportByTime(LocalDateTime end) {
        return monitoring.accept(new ReportByTimeGenerator(end));
    }

    @Override
    public Report startReporting() {
        return monitoring.accept(new ReportStartStopGenerator(true));
    }

    @Override
    public Report stopReporting() {
        return monitoring.accept(new ReportStartStopGenerator(false));

    }
}

