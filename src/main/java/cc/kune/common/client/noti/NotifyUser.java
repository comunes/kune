package cc.kune.common.client.noti;

import cc.kune.common.client.utils.SimpleCallback;
import cc.kune.core.client.notify.msgs.UserNotifyEvent;
import cc.kune.core.client.notify.spiner.ProgressHideEvent;
import cc.kune.core.client.notify.spiner.ProgressShowEvent;

import com.google.gwt.event.shared.EventBus;

public class NotifyUser {
    private static EventBus eventBus;
    private static SimpleCallback onOk;

    public static void hideProgress() {
        eventBus.fireEvent(new ProgressHideEvent());
    }

    public static void important(final String message) {
        eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.important, message));
    }

    public static void info(final String message) {
        eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.info, message));
    }

    public static void init(final EventBus eventBus) {
        NotifyUser.eventBus = eventBus;
        onOk = new SimpleCallback() {

            @Override
            public void onCancel() {
                // Do nothing
            }

            @Override
            public void onSuccess() {
                // Do nothing
            }
        };

    }

    public static void showAlertMessage(final String title, final String message) {
        eventBus.fireEvent(new AlertEvent(title, message, onOk));
    }

    public static void showAlertMessage(final String title, final String message, final SimpleCallback callback) {
        eventBus.fireEvent(new AlertEvent(title, message, callback));
    }

    public static void showProgress(final String text) {
        eventBus.fireEvent(new ProgressShowEvent(text));
    }

    public static void showProgressLoading() {
        eventBus.fireEvent(new ProgressShowEvent());
    }

    public static void showProgressProcessing() {
        eventBus.fireEvent(new ProgressShowEvent());
    }

    public static void veryImportant(final String message) {
        eventBus.fireEvent(new UserNotifyEvent(NotifyLevel.veryImportant, message));
    }

}
