package com.example.activitymonitor.report.application;

import com.example.activitymonitor.report.domain.Report;

public interface AbstractReportFactory {
    Report generateReport();
}
