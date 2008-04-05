
package org.ourproject.kune.platf.client.rpc;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MockedService {
    public static boolean isTest;

    public static interface Delayer {
        void run();
    }

    @SuppressWarnings("unchecked")
    protected void answer(final Object response, final AsyncCallback callback) {
        delay(new Delayer() {
            public void run() {
                callback.onSuccess(response);
            }
        });
    }

    protected void delay(final Delayer timer) {
        if (isTest) {
            timer.run();
        } else {
            schedule(timer);
        }
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
