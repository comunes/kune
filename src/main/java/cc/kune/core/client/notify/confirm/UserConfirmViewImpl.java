package cc.kune.core.client.notify.confirm;

import org.ourproject.common.client.notify.ConfirmationAsk;

import cc.kune.core.client.notify.confirm.UserConfirmPresenter.UserConfirmView;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class UserConfirmViewImpl extends ViewImpl implements UserConfirmView {
    @Inject
    public UserConfirmViewImpl() {
    }

    @Override
    public Widget asWidget() {
        return null;
    }

    @Override
    public void confirmationAsk(final ConfirmationAsk<?> ask) {
        // TODO Auto-generated method stub

    }

}
