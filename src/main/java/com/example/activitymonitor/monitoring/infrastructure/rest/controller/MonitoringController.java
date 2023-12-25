package com.example.activitymonitor.monitoring.infrastructure.rest.controller;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.abstractfactory.AbstractMonitor;
import com.example.activitymonitor.monitoring.infrastructure.rest.dto.MonitoringRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController("/monitoring")
public class MonitoringController {

    private final Map<String, AbstractMonitor> monitorMap;

    public MonitoringController(@Autowired List<AbstractMonitor> monitorList) {
        this.monitorMap = monitorList
                .stream()
                .collect(Collectors.toMap(AbstractMonitor::getOsType, Function.identity()));
    }

    @PostMapping
    public ResponseEntity<Void> startMonitoring(@RequestBody MonitoringRequestDto monitoringRequestDto) {
        String monitoringType = monitoringRequestDto.getMonitoringType();
        String osType = monitoringRequestDto.getOsType();

        AbstractMonitor abstractMonitor = monitorMap.get(osType);

        abstractMonitor.startMonitoring(monitoringType);

        return ResponseEntity.ok().build();
    }
}

