package com.example.activitymonitor.monitoring.application.service.jnative;

import com.example.activitymonitor.monitoring.domain.points.MouseTrackerMonitoringPoint;
import lombok.Getter;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.logging.Logger;

@Getter
public class MouseTracker implements NativeMouseMotionListener {
    private FluxSink<MouseTrackerMonitoringPoint> codsSink;
    private Flux<MouseTrackerMonitoringPoint> cordsFlux;

    public MouseTracker() {
        this.cordsFlux = Flux.<MouseTrackerMonitoringPoint>create(sink -> this.codsSink = sink).share();
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeEvent) {
        codsSink.next(new MouseTrackerMonitoringPoint(
                String.valueOf(nativeEvent.getX()),
                String.valueOf(nativeEvent.getY())));
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeEvent) {

    }



    static Logger logger = Logger.getLogger(MouseTracker.class.getName());
}
