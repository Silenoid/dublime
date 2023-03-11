package com.silenoids.control;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Sandglass {

    private static Sandglass instance;

    private Timer timer;
    private TimerTask scheduledTask;
    private JProgressBar jProgressBar;

    public static Sandglass getInstance(JProgressBar jProgressBar) {
        if (instance == null) {
            instance = new Sandglass(jProgressBar);
        }
        return instance;
    }

    public static Sandglass getInstance() {
        if (instance == null) {
            throw new IllegalStateException("As first call, you have to pass a JProgressBar");
        }
        return instance;
    }

    private Sandglass(JProgressBar jProgressBar) {
        timer = new Timer("Sandglass");
        this.jProgressBar = jProgressBar;
    }

    public void startSandglass(long timeInMillis) {
        if (timeInMillis <= 0) return;
        if (scheduledTask != null) scheduledTask.cancel();

        long updatePeriodInMillis = 10;

        scheduledTask = new TimerTask() {
            private long remainingTime = timeInMillis;

            @Override
            public void run() {
                remainingTime -= updatePeriodInMillis;
                if (remainingTime > 0) {
                    jProgressBar.setValue(Math.round((1 - (remainingTime / (float) timeInMillis)) * 100));
                } else {
                    jProgressBar.setValue(0);
                    this.cancel();
                }
            }
        };

        timer.scheduleAtFixedRate(scheduledTask, 0, updatePeriodInMillis);
    }
}
