package cc.kune.core.client.notify;

import org.ourproject.common.client.notify.ConfirmationAsk;
import org.ourproject.common.client.notify.NotifyLevel;

import cc.kune.core.client.notify.AlertEvent.AlertHandler;
import cc.kune.core.client.notify.UserNotifierPresenter.UserNotifierProxy;
import cc.kune.core.client.notify.UserNotifierPresenter.UserNotifierView;
import cc.kune.core.client.notify.UserNotifyEvent.UserNotifyHandler;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;

public class UserNotifierPresenter extends
		Presenter<UserNotifierView, UserNotifierProxy> {
	@ProxyCodeSplit
	public interface UserNotifierProxy extends
			Proxy<UserNotifierPresenter> {
	}

	public interface UserNotifierView extends View {
		public void alert(String title, String message);

		public void confirmationAsk(ConfirmationAsk<?> ask);

		public void notify(NotifyLevel level, String message);
	}

	@Inject
	public UserNotifierPresenter(final EventBus eventBus,
			final UserNotifierView view, final UserNotifierProxy proxy) {
		super(eventBus, view, proxy);
		addRegisteredHandler(UserNotifyEvent.getType(),
				new UserNotifyHandler() {
					@Override
					public void onUserNotify(UserNotifyEvent event) {
						view.notify(event.getLevel(), event.getMessage());
					}
				});
		addRegisteredHandler(AlertEvent.getType(), new AlertHandler() {
			@Override
			public void onAlert(AlertEvent event) {
				view.alert(event.getTitle(), event.getMessage());
			}
		});
	}

	@ProxyEvent
	public void onUserNotify(UserNotifyEvent event) {
		// FIXME test this
		getView().notify(event.getLevel(), event.getMessage());
	}

	@ProxyEvent
	public void onAlert(AlertEvent event) {
		getView().alert(event.getTitle(), event.getMessage());
	};

	@Override
	protected void revealInParent() {
	}

}