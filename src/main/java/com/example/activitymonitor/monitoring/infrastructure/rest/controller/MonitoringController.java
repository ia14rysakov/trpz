package com.example.activitymonitor.monitoring.infrastructure.rest.controller;

import com.example.activitymonitor.monitoring.application.abstractfactory.AbstractMonitor;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.infrastructure.rest.dto.MonitoringRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/monitoring")
@Slf4j
public class MonitoringController {
    private Logger logger = Logger.getLogger(MonitoringController.class.getName());

    private final Map<String, AbstractMonitor> monitorMap;

    public MonitoringController(@Autowired List<AbstractMonitor> monitorList) {
        this.monitorMap = monitorList
                .stream()
                .collect(Collectors.toMap(AbstractMonitor::getOsType, Function.identity()));
    }

    @GetMapping("/cpuLoad/{osType}")
    public Flux<MonitoringPoint> startCpuMonitoring(@PathVariable String osType) {
        logger.info("Monitoring request: cpuLoad on " + osType);


        AbstractMonitor abstractMonitor = monitorMap.get(osType);

        return abstractMonitor.startMonitoring("cpuLoad");
    }

    @PostMapping("/start")
    public Flux<MonitoringPoint> startMonitoring(@RequestBody MonitoringRequestDto monitoringRequestDto) {
        logger.info("Monitoring request: " + monitoringRequestDto.toString());
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
