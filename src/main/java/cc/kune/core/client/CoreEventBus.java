package cc.kune.core.client;

import org.ourproject.common.client.CommonGinModule;
import org.ourproject.common.client.notify.ConfirmationAsk;
import org.ourproject.common.client.notify.NotifyLevel;

import cc.kune.core.client.cookies.CookiesManager;
import cc.kune.core.client.notify.UserNotifierPresenter;
import cc.kune.core.client.ws.CorePresenter;
import cc.kune.core.client.ws.CoreView;

import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;

/**
 * The Kune Core EventBus.
 */
@Events(startView = CoreView.class, ginModules = { CoreGinModule.class, CommonGinModule.class })
@Debug(logLevel = LogLevel.DETAILED)
public interface CoreEventBus extends EventBus {

    @Event(handlers = UserNotifierPresenter.class)
    public void alert(String title, String message);

    @Event(handlers = UserNotifierPresenter.class)
    public void confirmationAsk(ConfirmationAsk<?> confirmation);

    @Event(handlers = CookiesManager.class)
    public void doNothing();

    @Event
    // FIXME (nobody do nothing with this)
    public void gotoToken(String token);

    @Event(handlers = UserNotifierPresenter.class)
    public void hideSpin();

    @Event
    // FIXME (nobody do nothing with this)
    public void i18nReady();

    @Event(handlers = UserNotifierPresenter.class)
    public void notify(NotifyLevel level, String message);

    @Event(handlers = UserNotifierPresenter.class)
    public void showSpin(String message);

    @Event
    // FIXME (nobody do nothing with this)
    public void showSpinLoading();

    /**
     * Application start event
     */
    @Start
    @Event(handlers = { CorePresenter.class })
    void start();

}
