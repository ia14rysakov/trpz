package com.example.activitymonitor.report.infrastructure.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ReportRequestDto {
    private String reportType;
    private String monitoringType;
    private String osType;
    private LocalDateTime dueToTime;
    private LocalDateTime scheduleStartTime;
    private LocalDateTime scheduleEndTime;
    private boolean isReportGoing;

    public ReportRequestDto(String reportType, String osType, String monitoringType) {
        this.monitoringType = monitoringType;
        this.reportType = reportType;
        this.osType = osType;
    }
}
