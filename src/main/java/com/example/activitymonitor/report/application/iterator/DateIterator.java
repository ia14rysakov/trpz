package com.example.activitymonitor.report.application.iterator;

import com.example.activitymonitor.report.domain.Report;

import java.time.LocalDateTime;
import java.util.List;

public class DateIterator implements ReportIterator {
    private final List<Report> reports;
    private int position;
    private final LocalDateTime lastDate;
    private final LocalDateTime firstDate;

    public DateIterator(List<Report> reports, LocalDateTime lastDate, LocalDateTime firstDate) {
        this.reports = reports;
        this.position = 0;
        this.lastDate = lastDate;
        this.firstDate = firstDate;
    }

    @Override
    public boolean hasNext() {
        while (position < reports.size()) {
            Report currentReport = reports.get(position);
            LocalDateTime reportDate = currentReport.getTimestamp();
            if (reportDate != null && !reportDate.isBefore(lastDate) && !reportDate.isAfter(firstDate)) {
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
