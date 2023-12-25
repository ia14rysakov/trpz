package com.example.activitymonitor.monitoring.infrastructure.rest.controller;

import com.example.activitymonitor.monitoring.application.abstractfactory.AbstractMonitor;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.infrastructure.rest.dto.MonitoringRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/monitoring")
public class MonitoringController {

    private final Map<String, AbstractMonitor> monitorMap;

    public MonitoringController(@Autowired List<AbstractMonitor> monitorList) {
        this.monitorMap = monitorList
                .stream()
                .collect(Collectors.toMap(AbstractMonitor::getOsType, Function.identity()));
    }

    @PostMapping("/start")
    public Flux<MonitoringPoint> startMonitoring(@RequestBody MonitoringRequestDto monitoringRequestDto) {
        String monitoringType = monitoringRequestDto.getMonitoringType();
        String osType = monitoringRequestDto.getOsType();

        AbstractMonitor abstractMonitor = monitorMap.get(osType);

        return abstractMonitor.startMonitoring(monitoringType);
    }

    @GetMapping("/test")
    public Flux<MonitoringPoint> test() {
        MonitoringRequestDto monitoringRequestDto = new MonitoringRequestDto("test", "Windows");
        String monitoringType = monitoringRequestDto.getMonitoringType();
        String osType = monitoringRequestDto.getOsType();

        AbstractMonitor abstractMonitor = monitorMap.get(osType);

        return abstractMonitor.startMonitoring(monitoringType);
    }
}
