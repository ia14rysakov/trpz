package com.example.activitymonitor.monitoring.application.service.jnative;

import lombok.Getter;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Getter
public class KeyListener implements NativeKeyListener {
    private List<String> keys = new ArrayList<>();

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        String keyText = NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode());
        keys.add(keyText);
        logger.log(java.util.logging.Level.INFO, "Key Pressed: " + keyText);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    static Logger logger = Logger.getLogger(KeyListener.class.getName());
}
