package cc.kune.core.client.notify;

import org.ourproject.common.client.notify.ConfirmationAsk;
import org.ourproject.common.client.notify.NotifyLevel;

import cc.kune.core.client.notify.UserNotifierPresenter.UserNotifierProxy;
import cc.kune.core.client.notify.UserNotifierPresenter.UserNotifierView;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class UserNotifierPresenter extends Presenter<UserNotifierView, UserNotifierProxy> {

    public interface UserNotifierProxy extends ProxyPlace<UserNotifierPresenter> {
    }

    public interface UserNotifierView extends View {
        public void alert(String title, String message);

        public void confirmationAsk(ConfirmationAsk<?> ask);

        public void notify(NotifyLevel level, String message);
    }

    @Inject
    public UserNotifierPresenter(final EventBus eventBus, final UserNotifierView view, final UserNotifierProxy proxy) {
        super(eventBus, view, proxy);
    }

    public void notify(final NotifyLevel level, final String message) {
        getView().notify(level, message);
    }

    public void alert(final String title, final String message) {
        getView().alert(title, message);
    };

    public void onConfirmationAsk(final ConfirmationAsk<?> ask) {
        getView().confirmationAsk(ask);
    }

    @Override
    protected void revealInParent() {
    }

}