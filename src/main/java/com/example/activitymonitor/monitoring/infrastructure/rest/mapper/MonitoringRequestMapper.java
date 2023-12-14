package com.example.activitymonitor.monitoring.infrastructure.rest.mapper;

import com.example.activitymonitor.monitoring.domain.MonitoringRequest;
import com.example.activitymonitor.monitoring.infrastructure.rest.dto.MonitoringRequestDto;

public class MonitoringRequestMapper {
    public static MonitoringRequest toDomain(MonitoringRequestDto monitoringRequestdto) {
        return new MonitoringRequest();
    }
}
