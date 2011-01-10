package cc.kune.core.client.notify.alerts;

import cc.kune.core.client.notify.alerts.UserAlerterPresenter.UserAlerterProxy;
import cc.kune.core.client.notify.alerts.UserAlerterPresenter.UserAlerterView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.Proxy;

public class UserAlerterPresenter extends Presenter<UserAlerterView, UserAlerterProxy> {
    @ProxyCodeSplit
    public interface UserAlerterProxy extends Proxy<UserAlerterPresenter> {
    }

    public interface UserAlerterView extends View {
        public void alert(String title, String message);
    }

    @Inject
    public UserAlerterPresenter(final EventBus eventBus, final UserAlerterView view, final UserAlerterProxy proxy) {
        super(eventBus, view, proxy);
    }

    @ProxyEvent
    public void onAlert(AlertEvent event) {
        getView().alert(event.getTitle(), event.getMessage());
    }

    @Override
    protected void revealInParent() {
        RootPanel.get().add(getWidget());
    }

}