package cc.kune.core.client.notify;

import org.ourproject.common.client.notify.ConfirmationAsk;
import org.ourproject.common.client.notify.NotifyLevel;

import cc.kune.core.client.CoreEventBus;
import cc.kune.core.client.notify.UserNotifierPresenter.IUserNotifierView;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.client.view.LazyView;

@Presenter(view = UserNotifierView.class)
public class UserNotifierPresenter extends LazyPresenter<IUserNotifierView, CoreEventBus> {

    public interface IUserNotifierView extends LazyView {
        public void alert(String title, String message);

        public void confirmationAsk(ConfirmationAsk<?> ask);

        public void hideSpin();

        public void notify(NotifyLevel level, String message);

        public void showSpin(String message);
    }

    public void onAlert(final String title, final String message) {
        view.alert(title, message);
    }

    public void onConfirmationAsk(final ConfirmationAsk<?> ask) {
        view.confirmationAsk(ask);
    };

    public void onHideSpin() {
        view.hideSpin();
    }

    public void onNotify(final NotifyLevel level, final String message) {
        view.notify(level, message);
    }

    public void onShowSpin(final String message) {
        view.showSpin(message);
    }
}