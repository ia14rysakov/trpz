package com.example.activitymonitor.monitoring.application.commands;

import com.example.activitymonitor.monitoring.application.output.StartMonitoringCommand;
import com.example.activitymonitor.monitoring.application.service.MemoryMonitoringService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoryMonitoringCommand implements StartMonitoringCommand {

        private MemoryMonitoringService memoryMonitoringService;
        private boolean isMonitoringStarted;

        public MemoryMonitoringCommand(MemoryMonitoringService memoryMonitoringService, boolean isMonitoringStarted) {
            this.memoryMonitoringService = memoryMonitoringService;
            this.isMonitoringStarted = isMonitoringStarted;
        }

        @Override
        public void execute() {
            memoryMonitoringService.startMonitoring(isMonitoringStarted);
        }
}

