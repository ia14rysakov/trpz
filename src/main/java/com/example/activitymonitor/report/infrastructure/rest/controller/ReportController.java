package com.example.activitymonitor.report.infrastructure.rest.controller;

import com.example.activitymonitor.monitoring.application.abstractfactory.AbstractMonitor;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.infrastructure.rest.dto.ReportRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
public class ReportController {

    private Map<String, ReportVisitor> reportVisitorMap;

    private Map<String, AbstractMonitor> monitorMap;

    public ReportController(List<ReportVisitor> reportVisitorMap, List<AbstractMonitor> monitorList) {

        this.reportVisitorMap = reportVisitorMap.stream()
                .collect(java.util.stream.Collectors.toMap(ReportVisitor::getReportName,
                        java.util.function.Function.identity()));

        this.monitorMap = monitorList
                .stream()
                .collect(Collectors.toMap(AbstractMonitor::getOsType, Function.identity()));
    }

    @PostMapping("")
    public ResponseEntity<Report> getReport(@RequestBody ReportRequestDto reportRequestDto) {
        String reportType = reportRequestDto.getReportType();

        AbstractMonitor currentMonitor = monitorMap.get(reportRequestDto.getOsType());

        ReportVisitor reportVisitor = reportVisitorMap.get(reportType);

        Report report = currentMonitor.getConcreteMonitoring(reportRequestDto).accept(reportVisitor);

        return ResponseEntity.ok(report);
    }
}
