package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.points.KeyLoggerMonitoringPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    public Stream<MonitoringPoint> startMonitoring(boolean isMonitoringStarted) {
        BlockingQueue<KeyLoggerMonitoringPoint> keyEventsQueue = new LinkedBlockingQueue<>();

        NativeKeyListener nativeKeyListener = new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                if (isMonitoringStarted) {
                    keyEventsQueue.add(new KeyLoggerMonitoringPoint(NativeKeyEvent.getKeyText(e.getKeyCode())));
                }
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent e) {
                // Implement as needed
            }

            @Override
            public void nativeKeyTyped(NativeKeyEvent e) {
                // Implement as needed
            }
        };

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(nativeKeyListener);

        return Stream.generate(() -> {
            try {
                return keyEventsQueue.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        });
    }
}
class GlobalKeyListenerExample implements NativeKeyListener {

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        // Implement as needed
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        // Implement as needed
    }

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListenerExample());
    }
}
