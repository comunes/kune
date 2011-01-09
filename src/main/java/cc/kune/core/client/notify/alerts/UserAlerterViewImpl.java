package cc.kune.core.client.notify.alerts;

import cc.kune.core.client.notify.alerts.UserAlerterPresenter.UserAlerterView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class UserAlerterViewImpl extends ViewImpl implements UserAlerterView {
    @Inject
    public UserAlerterViewImpl() {
    }

    @Override
    public void alert(final String title, final String message) {
        message(title, message);
    }

    @Override
    public Widget asWidget() {
        return null;
    }

    private void message(final String title, final String message) {
        Window.alert(title + " " + message);
    }

}
