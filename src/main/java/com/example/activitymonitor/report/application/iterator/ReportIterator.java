package com.example.activitymonitor.report.application.iterator;

import com.example.activitymonitor.report.domain.Report;

public interface ReportIterator {
    boolean hasNext();
    Report getNext();
}
