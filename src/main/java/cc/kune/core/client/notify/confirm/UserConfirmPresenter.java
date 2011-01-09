package cc.kune.core.client.notify.confirm;

import org.ourproject.common.client.notify.ConfirmationAsk;

import cc.kune.core.client.notify.confirm.UserConfirmPresenter.UserConfirmProxy;
import cc.kune.core.client.notify.confirm.UserConfirmPresenter.UserConfirmView;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;

public class UserConfirmPresenter extends Presenter<UserConfirmView, UserConfirmProxy> {
    @ProxyCodeSplit
    public interface UserConfirmProxy extends Proxy<UserConfirmPresenter> {
    }

    public interface UserConfirmView extends View {

        public void confirmationAsk(ConfirmationAsk<?> ask);
    }

    @Inject
    public UserConfirmPresenter(final EventBus eventBus, final UserConfirmView view, final UserConfirmProxy proxy) {
        super(eventBus, view, proxy);
    }

    @Override
    protected void revealInParent() {
        RootPanel.get().add(getWidget());
    }

}