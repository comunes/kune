package cc.kune.core.client.ws;

import cc.kune.core.client.i18n.I18nReadyEvent;
import cc.kune.core.client.init.AppStarter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
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
    @ProxyCodeSplit
    @NameToken(HOME_TOKEN)
    public interface CoreProxy extends ProxyPlace<CorePresenter> {
    }
    public interface CoreView extends View {
    }

    public static final String HOME_TOKEN = "";

    private final AppStarter appStarter;

    @Inject
    public CorePresenter(final EventBus eventBus, final CoreView view, final CoreProxy proxy, AppStarter appStarter) {
        super(eventBus, view, proxy);
        this.appStarter = appStarter;
    }

    @ProxyEvent
    public void onI18nReady(I18nReadyEvent event) {
        appStarter.start();
    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(this, this);
    }

}
