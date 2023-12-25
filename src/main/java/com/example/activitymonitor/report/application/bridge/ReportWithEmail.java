package com.example.activitymonitor.report.application.bridge;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.report.domain.Report;

import java.time.LocalDateTime;

/**
 * Deprecated due to useless in course work
 */

public class ReportWithEmail implements ReportCreator {
    public Monitoring monitoring;
    public String email;

    public ReportWithEmail(Monitoring monitoring) {
        this.monitoring = monitoring;
    }

    @Override
    public Report generateScheduledReport(LocalDateTime start, LocalDateTime end) {
        return null;
    }

    @Override
    public Report generateReportByTime(LocalDateTime end) {
        return null;
    }

    @Override
    public Report startReporting() {
        return null;
    }

    @Override
    public Report stopReporting() {
        return null;
    }
}

