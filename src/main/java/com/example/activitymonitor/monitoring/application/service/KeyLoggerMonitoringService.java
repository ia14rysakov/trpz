package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.application.service.jnative.KeyListener;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.points.KeyLoggerMonitoringPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import reactor.core.publisher.Flux;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class KeyLoggerMonitoringService implements Monitoring {

    @Override
    public String getMonitoringName() {
        return "KeyLoggerMonitoring";
    }

    @Override
    public Report accept(ReportVisitor reportVisitor) {
        return reportVisitor.visit(this);
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

        return Flux.defer(() -> Flux.fromIterable(keyListener.getKeys()))
                .map(KeyLoggerMonitoringPoint::new);
    }
}
