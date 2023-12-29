//package com.example.activitymonitor.report.application.visitor;
//
//import com.example.activitymonitor.monitoring.application.Monitoring;
//import com.example.activitymonitor.monitoring.application.service.*;
//import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
//import com.example.activitymonitor.report.domain.Report;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Setter
//@Getter
//@Component
//public class ScheduledReportGenerator implements ReportVisitor {
//
//    private LocalDateTime start;
//    private LocalDateTime end;
//
//    @Override
//    public String getReportName() {
//        return "ScheduledReport";
//    }
//
//    @Override
//    public Mono<Report> visit(CpuLoadMonitoringService cpuLoadMonitoringService) {
//        return defaultVisiting(cpuLoadMonitoringService, "CPU Load Monitoring");
//    }
//
//    @Override
//    public Mono<Report> visit(KeyLoggerMonitoringService keyLoggerMonitoringService) {
//
//        return defaultVisiting(keyLoggerMonitoringService, "Key Logger Monitoring");
//    }
//
//    @Override
//    public Mono<Report> visit(MemoryMonitoringService memoryMonitoringService) {
//
//        return defaultVisiting(memoryMonitoringService, "Memory Monitoring");
//    }
//
//    @Override
//    public Mono<Report> visit(MouseTrackerMonitoringService mouseTrackerMonitoringService) {
//
//        return defaultVisiting(mouseTrackerMonitoringService, "Mouse Tracker Monitoring");
//    }
//
//    @Override
//    public Mono<Report> visit(WindowsMonitoringService windowsMonitoringService) {
//
//        return defaultVisiting(windowsMonitoringService, "Windows Monitoring");
//    }
//
//    @Override
//    public Mono<Report> visit(TestMonitoring testMonitoring) {
//        return defaultVisiting(testMonitoring, "Test Monitoring");
//    }
//
//    private Mono<Report> defaultVisiting(Monitoring monitoring, String serviceName) {
//
//        if (start == null || end == null) {
//            throw new IllegalStateException("Start and end times must be set before generating a report");
//        }
//
//        Duration delay = Duration.between(LocalDateTime.now(), start);
//        Mono<Long> delayMono = Mono.delay(delay);
//
//        Flux<MonitoringPoint> dataFlux = monitoring.startMonitoring(true)
//                .takeWhile(point -> LocalDateTime.now().isBefore(end));
//
//        return delayMono.thenMany(dataFlux).collectList().map(data -> {
//            LocalDateTime endTime = LocalDateTime.now();
//            Duration duration = Duration.between(start, endTime);
//
//            String title = serviceName + " Report - from " + start + " to " + end;
//
//            Report report = new Report(title, duration, getReportName());
//            report.setData(data);
//
//            return report;
//        });
//    }
//}
