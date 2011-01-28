package cc.kune.common.client.utils;

import com.google.gwt.user.client.Timer;

public class TimerWrapper {
    public interface Executer {
        /**
         * Invokes the execute.
         */
        void execute();
    }

    private Timer timer;

    public TimerWrapper() {
    }

    public void cancel() {
        timer.cancel();
    }

    public void configure(final Executer onTime) {
        timer = new Timer() {
            @Override
            public void run() {
                onTime.execute();
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
