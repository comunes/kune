package cc.kune.core.client.ws;

import cc.kune.core.client.i18n.I18nReadyEvent;
import cc.kune.core.client.init.AppStarter;
import cc.kune.core.client.notify.SpinerPresenter;
import cc.kune.core.client.notify.UserNotifierPresenter;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

/**
 * The Class CorePresenter.
 */
public class CorePresenter extends Presenter<CorePresenter.CoreView, CorePresenter.CoreProxy> {
    private final AppStarter appStarter;

    @ProxyCodeSplit
    @NameToken("home")
    public interface CoreProxy extends ProxyPlace<CorePresenter> {
    }

    public interface CoreView extends View {
    }

    @Inject
    public CorePresenter(final EventBus eventBus, final CoreView view, final CoreProxy proxy,
            final I18nTranslationService i18n, UserNotifierPresenter userNotifier, SpinerPresenter spiner,
            AppStarter appStarter) {
        super(eventBus, view, proxy);
        this.appStarter = appStarter;
    }

    @ProxyEvent
    public void onI18nReady(I18nReadyEvent event) {
        appStarter.start();
    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(getEventBus(), this);
        // getEventBus().fireEvent(new ProgressShowEvent("Something"));
        // Timer timer = new Timer() {
        // @Override
        // public void run() {
        // getEventBus().fireEvent(new ProgressHideEvent());
        // }
        // };
        // // timer.schedule(4);
    }

}
