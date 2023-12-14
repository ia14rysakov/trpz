package com.example.activitymonitor.monitoring.infrastructure.rest.controller;

import com.example.activitymonitor.monitoring.application.input.AbstractMonitoringFactory;
import com.example.activitymonitor.monitoring.application.input.Invoker;
import com.example.activitymonitor.monitoring.infrastructure.rest.dto.MonitoringRequestDto;
import com.example.activitymonitor.monitoring.infrastructure.rest.mapper.MonitoringRequestMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/monitoring")
public class MonitoringController {

    private Invoker invoker;


    @PostMapping
    public ResponseEntity<Void> startMonitoring(@RequestBody MonitoringRequestDto monitoringRequestDto) {
        invoker.invoke(MonitoringRequestMapper.toDomain(monitoringRequestDto));

        return ResponseEntity.ok().build();
    }
}
