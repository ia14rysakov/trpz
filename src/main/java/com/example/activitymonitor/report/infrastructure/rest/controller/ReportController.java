package com.example.activitymonitor.report.infrastructure.rest.controller;

import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.infrastructure.rest.dto.ReportRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/report")
public class ReportController {



    @PostMapping("")
    public ResponseEntity<Report> getReport(@RequestBody ReportRequestDto reportRequestDto) {

        return ResponseEntity.noContent().build();
    }
}
