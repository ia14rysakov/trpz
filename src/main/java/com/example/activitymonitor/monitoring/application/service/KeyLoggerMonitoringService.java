package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.service.jnative.KeyListener;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.MonitoringType;
import com.example.activitymonitor.monitoring.domain.points.KeyLoggerMonitoringPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.infrastructure.rest.dto.ReportRequestDto;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class KeyLoggerMonitoringService implements Monitoring {

    @Override
    public String getMonitoringName() {
        return MonitoringType.KEYBOARD_ACTIVITY.name();
    }

    @Override
    public Mono<Report> accept(ReportVisitor reportVisitor, ReportRequestDto reportRequestDto) {
        return reportVisitor.visit(this, reportRequestDto);
    }

    @Override
    public Flux<MonitoringPoint> startMonitoring(boolean isMonitoringStarted) {
        KeyListener keyListener = new KeyListener();

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(keyListener);

        return keyListener.getKeysFlux()
                .map(key -> (MonitoringPoint) new KeyLoggerMonitoringPoint(key))
                .takeWhile(key -> isMonitoringStarted);
    }
}
