package cc.kune.common.client.tooltip;

import cc.kune.common.client.utils.TimerWrapper;

public class TooltipTimers {

    private final TimerWrapper hideTimer;
    private final TimerWrapper securityTimer;
    private final TimerWrapper showTimer;

    public TooltipTimers(final TimerWrapper showTimer, final TimerWrapper hideTimer, final TimerWrapper securityTimer) {
        this.showTimer = showTimer;
        this.hideTimer = hideTimer;
        this.securityTimer = securityTimer;
    }

    public void onOut() {
        if (showTimer.isScheduled()) {
            showTimer.cancel();
        }
        if (!hideTimer.isScheduled()) {
            hideTimer.schedule(650);
        }
        if (!securityTimer.isScheduled()) {
            securityTimer.cancel();
        }
    }

    public void onOver() {
        if (!showTimer.isScheduled()) {
            showTimer.schedule(700);
        }
        if (hideTimer.isScheduled()) {
            hideTimer.cancel();
        }
        if (!securityTimer.isScheduled()) {
            securityTimer.schedule(7000);
        }
    }
}
