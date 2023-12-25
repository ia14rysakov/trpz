package com.example.activitymonitor.monitoring.infrastructure.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitoringRequestDto {

    private String monitoringType;
    private String osType;

    public MonitoringRequestDto(String monitoringType, String osType) {
        this.monitoringType = monitoringType;
        this.osType = osType;
    }
}

