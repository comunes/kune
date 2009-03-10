package org.ourproject.kune.platf.client.utils;

import com.calclab.suco.client.events.Listener0;
import com.google.gwt.user.client.Timer;

public class TimerWrapper {
    private Timer timer;

    public TimerWrapper() {
    }

    public void cancel() {
        timer.cancel();
    }

    public void configure(final Listener0 onTime) {
        timer = new Timer() {
            @Override
            public void run() {
                onTime.onEvent();
            }
        };
    }

    public void run() {
        timer.run();
    }

    public void schedule(final int delayMillis) {
        timer.schedule(delayMillis);
    }

    public void scheduleRepeating(final int delayMillis) {
        timer.scheduleRepeating(delayMillis);
    }
}
