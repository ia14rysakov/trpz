package com.example.activitymonitor.report.infrastructure.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportRequestDto {
    private String reportType;
    private String monitoringType;
    private String osType;

    public ReportRequestDto(String reportType, String osType, String monitoringType) {
        this.monitoringType = monitoringType;
        this.reportType = reportType;
        this.osType = osType;
    }
}
