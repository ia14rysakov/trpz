package com.example.activitymonitor.monitoring.application.service.jnative;

import lombok.Getter;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Getter
public class KeyListener implements NativeKeyListener {
    private FluxSink<String> keysSink;
    private Flux<String> keysFlux;

    public KeyListener() {
        this.keysFlux = Flux.<String>create(sink -> this.keysSink = sink).share();
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        String keyText = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode());
        keysSink.next(keyText);
        logger.log(java.util.logging.Level.INFO, "Key Typed: " + keyText);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        String keyText = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode());
        keysSink.next(keyText);
        Logger.getLogger(KeyListener.class.getName()).info("Key Pressed: " + keyText);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    static Logger logger = Logger.getLogger(KeyListener.class.getName());
}
