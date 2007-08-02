package org.ourproject.kune.platf.client.rpc;

import com.google.gwt.user.client.Timer;

public class MockedService {
    public static boolean isTest;

    public static interface Delayer {
	void run();
    }

    protected void delay(Delayer timer) {
	if (isTest)
	    timer.run();
	else
	    schedule(timer);
    }

    private void schedule(final Delayer delayer) {
	Timer timer = new Timer() {
	    public void run() {
		delayer.run();
	    }
	};
	timer.schedule(1500);
    }
}
