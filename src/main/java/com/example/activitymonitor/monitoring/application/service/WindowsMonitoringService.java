package com.example.activitymonitor.monitoring.application.service;


import com.example.activitymonitor.monitoring.application.Monitoring;
import com.example.activitymonitor.monitoring.domain.MonitoringPoint;
import com.example.activitymonitor.monitoring.domain.points.WindowsMonitoringPoint;
import com.example.activitymonitor.monitoring.domain.points.WindowsPoint;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class WindowsMonitoringService implements Monitoring {

    @Override
    public String getMonitoringName() {
        return "WindowsMonitoring";
    }

    @Override
    public Report accept(ReportVisitor reportVisitor) {
        return reportVisitor.visit(this);
    }

    @Override
    public Stream<MonitoringPoint> startMonitoring(boolean isMonitoringStarted) {
        return Stream.generate(() -> {
            List<WindowsPoint> windowsMonitoringPoints = new ArrayList<>();
            if (isMonitoringStarted) {
                User32.INSTANCE.EnumWindows((hWnd, arg) -> {
                    char[] windowText = new char[512];
                    User32.INSTANCE.GetWindowText(hWnd, windowText, 512);
                    String wText = Native.toString(windowText);

                    WinDef.RECT rect = new WinDef.RECT();
                    User32.INSTANCE.GetWindowRect(hWnd, rect);
                    String windowSize = rect.right + "x" + rect.bottom;

                    OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
                    double cpuUsage = osBean.getSystemLoadAverage();

                    Runtime runtime = Runtime.getRuntime();
                    double memoryUsage = (double) (runtime.totalMemory() - runtime.freeMemory()) / runtime.totalMemory();

                    windowsMonitoringPoints.add(new WindowsPoint(wText, windowSize, cpuUsage, memoryUsage));
                    return true;
                }, null);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            return new WindowsMonitoringPoint(windowsMonitoringPoints);
        });
    }
}

