package com.example.activitymonitor.monitoring.application.service;


import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.MonitoringType;
import com.example.activitymonitor.monitoring.domain.points.WindowsMonitoringPoint;
import com.example.activitymonitor.monitoring.domain.points.WindowsPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.infrastructure.rest.dto.ReportRequestDto;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WindowsMonitoringService implements Monitoring {

    @Override
    public String getMonitoringName() {
        return MonitoringType.WINDOWS.name();
    }

    @Override
    public Mono<Report> accept(ReportVisitor reportVisitor, ReportRequestDto reportRequestDto) {
        return reportVisitor.visit(this, reportRequestDto);
    }

    @Override
    public Flux<MonitoringPoint> startMonitoring(boolean isMonitoringStarted) {
        return Flux.generate(sink -> {
            if (isMonitoringStarted) {
                List<WindowsPoint> windowsMonitoringPoints = new ArrayList<>();
                User32.INSTANCE.EnumWindows((hWnd, arg) -> {
                    char[] windowText = new char[512];
                    User32.INSTANCE.GetWindowText(hWnd, windowText, 512);
                    String wText = Native.toString(windowText);

                    WinDef.RECT rect = new WinDef.RECT();
                    User32.INSTANCE.GetWindowRect(hWnd, rect);
                    String windowSize = rect.right + "x" + rect.bottom;

                    if (!wText.isBlank()) {
                        windowsMonitoringPoints.add(new WindowsPoint(wText, windowSize));
                    }
                    return true;
                }, null);
                sink.next(new WindowsMonitoringPoint(windowsMonitoringPoints));
            } else {
                sink.complete();
            }
        }).delayElements(Duration.ofSeconds(1)).cast(MonitoringPoint.class);
    }
}
