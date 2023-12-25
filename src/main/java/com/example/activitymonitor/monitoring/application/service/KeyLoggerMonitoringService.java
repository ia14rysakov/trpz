package com.example.activitymonitor.monitoring.application.service;

import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.points.KeyLoggerMonitoringPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;

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

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (isMonitoringStarted) {
                    keyEventsQueue.add(new KeyLoggerMonitoringPoint(String.valueOf(e.getKeyChar())));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        // Add the key listener to the AWT event queue
        Toolkit.getDefaultToolkit().addAWTEventListener(e -> {
            if (e instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) e;
                if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
                    keyListener.keyPressed(keyEvent);
                }
            }
        }, AWTEvent.KEY_EVENT_MASK);

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
