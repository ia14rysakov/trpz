package com.example.activitymonitor.report.application;

import com.example.activitymonitor.report.domain.Report;

import java.time.LocalDateTime;

interface ReportCreator {
    Report generateScheduledReport(LocalDateTime start, LocalDateTime end);

    Report generateReportByTime(LocalDateTime end);

    Report startReporting();

    Report stopReporting();
}

