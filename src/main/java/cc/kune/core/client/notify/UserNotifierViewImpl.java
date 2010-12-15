package cc.kune.core.client.notify;

import org.ourproject.common.client.notify.ConfirmationAsk;
import org.ourproject.common.client.notify.NotifyLevel;

import cc.kune.core.client.notify.UserNotifierPresenter.UserNotifierView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class UserNotifierViewImpl extends ViewImpl implements UserNotifierView {
    @Inject
    public UserNotifierViewImpl() {
    }

    @Override
    public void alert(final String title, final String message) {
        message(title, message);
    }

    @Override
    public Widget asWidget() {
        return null;
    }

    @Override
    public void confirmationAsk(final ConfirmationAsk<?> ask) {
        // TODO Auto-generated method stub

    }

    @Override
    public void notify(final NotifyLevel level, final String message) {
        message("", message);
    }

    private void message(final String title, final String message) {
        Window.alert(title + " " + message);
    }

}
