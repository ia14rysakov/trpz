package com.example.activitymonitor.report.application.bridge;

import com.example.activitymonitor.report.domain.Report;

import java.time.LocalDateTime;

/**
 * Deprecated due to useless in course work
 */

interface ReportCreator {
    Report generateScheduledReport(LocalDateTime start, LocalDateTime end);

    Report generateReportByTime(LocalDateTime end);

    Report startReporting();

    Report stopReporting();
}

