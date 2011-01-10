package cc.kune.core.client.notify.confirm;

import cc.kune.common.client.noti.ConfirmAskEvent;
import cc.kune.core.client.notify.confirm.UserConfirmPresenter.UserConfirmView;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class UserConfirmViewImpl2 extends ViewImpl implements UserConfirmView {
    @Inject
    public UserConfirmViewImpl2() {
    }

    @Override
    public Widget asWidget() {
        return null;
    }

    @Override
    public void confirmAsk(final ConfirmAskEvent ask) {
        // TODO Auto-generated method stub

    }

}
