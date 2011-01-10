package org.ourproject.kune.platf.client.ui.noti;

import com.calclab.suco.client.events.Listener0;

@Deprecated
public class ConfirmationAsk {

    private final String title;
    private final String message;
    private final Listener0 onConfirmed;
    private final Listener0 onCancel;

    public ConfirmationAsk(final String title, final String message, final Listener0 onConfirmed,
            final Listener0 onCancel) {
        this.title = title;
        this.message = message;
        this.onConfirmed = onConfirmed;
        this.onCancel = onCancel;
    }

    public String getMessage() {
        return message;
    }

    public Listener0 getOnCancel() {
        return onCancel;
    }

    public Listener0 getOnConfirmed() {
        return onConfirmed;
    }

    public String getTitle() {
        return title;
    }
}
