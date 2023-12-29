package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.service.jnative.KeyListener;
import com.example.activitymonitor.monitoring.application.service.jnative.MouseTracker;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.points.KeyLoggerMonitoringPoint;
import com.example.activitymonitor.monitoring.domain.points.MouseTrackerMonitoringPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class MouseTrackerMonitoringService implements Monitoring {

    private volatile MouseTrackerMonitoringPoint lastEmittedPoint = null;


    @Override
    public String getMonitoringName() {
        return "MouseTracker";
    }

    @Override
    public Mono<Report> accept(ReportVisitor reportVisitor) {
        return reportVisitor.visit(this);
    }

    @Override
    public Flux<MonitoringPoint> startMonitoring(boolean isMonitoringStarted) {
        MouseTracker mouseTracker = new MouseTracker();

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeMouseMotionListener(mouseTracker);

        return mouseTracker.getCordsFlux()
                .cast(MonitoringPoint.class)
                .window(Duration.ofSeconds(1))
                .flatMap(Flux::last)
                .onErrorContinue((throwable, o) -> {})
                .takeWhile(key -> isMonitoringStarted);
    }
}
