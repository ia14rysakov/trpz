package com.example.activitymonitor.report.application.iterator;

import com.example.activitymonitor.report.domain.Report;

import java.util.Iterator;
import java.util.List;

public class DateIterator implements Iterator<Report> {
    private List<Report> reports;
    private int position;

    public DateIterator(List<Report> reports) {
        this.reports = reports;
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        while (position < reports.size()) {
            if (reports.get(position) != null) {
                return true;
            }
            position++;
        }
        return false;
    }

    @Override
    public Report next() {
        return reports.get(position++);
    }
}
