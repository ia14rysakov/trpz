package com.example.activitymonitor.report.application.iterator;

import com.example.activitymonitor.report.domain.Report;

import java.util.List;

public class DataPointCountIterator {
    private final List<Report> reports;
    private int position;
    private final int minDataPointCount;

    public DataPointCountIterator(List<Report> reports, int minDataPointCount) {
        this.reports = reports;
        this.position = 0;
        this.minDataPointCount = minDataPointCount;
    }

    public boolean hasNext() {
        while (position < reports.size()) {
            Report currentReport = reports.get(position);
            if (currentReport.getData().size() > minDataPointCount) {
                return true;
            }
            position++;
        }
        return false;
    }

    public Report getNext() {
        if (!hasNext()) {
            return null;
        }
        return reports.get(position++);
    }
}
