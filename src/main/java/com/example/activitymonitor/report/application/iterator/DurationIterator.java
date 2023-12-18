package com.example.activitymonitor.report.application.iterator;

import com.example.activitymonitor.report.domain.Report;

import java.time.Duration;
import java.util.List;

public class DurationIterator implements ReportIterator {
    private final List<Report> reports;
    private int position;
    private final Duration targetDuration;
    private final boolean isDurationLower;

    public DurationIterator(List<Report> reports, Duration targetDuration, boolean isDurationLower) {
        this.reports = reports;
        this.position = 0;
        this.targetDuration = targetDuration;
        this.isDurationLower = isDurationLower;
    }

    @Override
    public boolean hasNext() {
        while (position < reports.size()) {
            Report currentReport = reports.get(position);
            Duration reportDuration = currentReport.getDuration();
            if (reportDuration != null &&
                    (isDurationLower ? reportDuration.compareTo(targetDuration) < 0 :
                            reportDuration.compareTo(targetDuration) > 0)) {
                return true;
            }
            position++;
        }
        return false;
    }

    @Override
    public Report getNext() {
        if (!hasNext()) {
            return null;
        }
        return reports.get(position++);
    }
}

