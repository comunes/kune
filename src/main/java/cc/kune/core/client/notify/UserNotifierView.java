package cc.kune.core.client.notify;

import org.ourproject.common.client.notify.ConfirmationAsk;
import org.ourproject.common.client.notify.NotifyLevel;

import cc.kune.core.client.notify.UserNotifierPresenter.IUserNotifierView;

import com.google.gwt.user.client.Window;

public class UserNotifierView implements IUserNotifierView {

    @Override
    public void alert(final String title, final String message) {
        message(title, message);
    }

    @Override
    public void confirmationAsk(final ConfirmationAsk<?> ask) {
        // TODO Auto-generated method stub

    }

    @Override
    public void createView() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hideSpin() {
        // TODO Auto-generated method stub

    }

    @Override
    public void notify(final NotifyLevel level, final String message) {
        message("", message);
    }

    @Override
    public void showSpin(final String message) {
        // TODO Auto-generated method stub

    }

    private void message(final String title, final String message) {
        Window.alert(title + " " + message);
    }

}
