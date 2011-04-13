package cc.kune.common.client.tooltip;

import cc.kune.common.client.utils.TimerWrapper;

public class TooltipTimers {

    private final TimerWrapper hideTimer;
    private final TimerWrapper showTimer;

    public TooltipTimers(final TimerWrapper showTimer, final TimerWrapper hideTimer) {
        this.showTimer = showTimer;
        this.hideTimer = hideTimer;
    }

    public void onOut() {
        if (showTimer.isScheduled()) {
            showTimer.cancel();
        }
        if (!hideTimer.isScheduled()) {
            hideTimer.schedule(650);
        }
    }

    public void onOver() {
        if (!showTimer.isScheduled()) {
            showTimer.schedule(500);
        }
        if (hideTimer.isScheduled()) {
            hideTimer.cancel();
        }
    }
}
