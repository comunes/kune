package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;

public abstract class AbstractPresenter {

    public void doAction(final String action, final Object value) {
        DefaultDispatcher.getInstance().fire(action, value);
    }

    public void doGoto(final String token) {
        DefaultDispatcher.getInstance().fire(PlatformEvents.GOTO, token);
    }

}
