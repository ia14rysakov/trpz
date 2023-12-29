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
    private final Logger logger = Logger.getLogger(MonitoringController.class.getName());

    private String osType = "Windows";

    private final Map<String, AbstractMonitor> monitorMap;

    public MonitoringController(@Autowired List<AbstractMonitor> monitorList) {
        this.monitorMap = monitorList
                .stream()
                .collect(Collectors.toMap(AbstractMonitor::getOsType, Function.identity()));
    }

    @GetMapping("/cpuLoad")
    public Flux<MonitoringPoint> startCpuMonitoring() {
        logger.info("Starting cpu load monitoring");

       MonitoringRequestDto monitoringRequestDto = new MonitoringRequestDto("cpuLoad", "Windows");

        return getMonitoringPoints(monitoringRequestDto);
    }

    @GetMapping("/memory")
    public Flux<MonitoringPoint> startMemoryMonitoring() {
        logger.info("Monitoring request: cpuLoad on " + osType);

        MonitoringRequestDto monitoringRequestDto = new MonitoringRequestDto("memory", "Windows");

        return getMonitoringPoints(monitoringRequestDto);
    }

    @GetMapping("/keyLogger")
    public Flux<MonitoringPoint> startKeyLogger() {
        logger.info("Monitoring request: cpuLoad on " + osType);

        MonitoringRequestDto monitoringRequestDto = new MonitoringRequestDto("keyLogger", "Windows");

        return getMonitoringPoints(monitoringRequestDto);
    }

    @GetMapping("/windows")
    public Flux<MonitoringPoint> startWindowsMonitoring() {
        logger.info("Monitoring request: cpuLoad on " + osType);

        MonitoringRequestDto monitoringRequestDto = new MonitoringRequestDto("windows", "Windows");

        return getMonitoringPoints(monitoringRequestDto);
    }

    @GetMapping("/mouseTracker")
    public Flux<MonitoringPoint> startMouseTracker() {
        logger.info("Monitoring request: cpuLoad on " + osType);

        MonitoringRequestDto monitoringRequestDto = new MonitoringRequestDto("mouseTracker", "Windows");

        return getMonitoringPoints(monitoringRequestDto);
    }

    @GetMapping("/test")
    public Flux<MonitoringPoint> test() {
        MonitoringRequestDto monitoringRequestDto = new MonitoringRequestDto("test", "Windows");

        return getMonitoringPoints(monitoringRequestDto);
    }

    private Flux<MonitoringPoint> getMonitoringPoints(MonitoringRequestDto monitoringRequestDto) {
        String monitoringType = monitoringRequestDto.getMonitoringType();
        String osType = monitoringRequestDto.getOsType();

        AbstractMonitor abstractMonitor = monitorMap.get(osType);

        return abstractMonitor.startMonitoring(monitoringType);
    }
}
