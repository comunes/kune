package cc.kune.core.client.ws;

import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

/**
 * The Class CorePresenter.
 */
public class CorePresenter extends Presenter<CorePresenter.CoreView, CorePresenter.CoreProxy> {
    @ProxyCodeSplit
    @NameToken("home")
    public interface CoreProxy extends ProxyPlace<CorePresenter> {
    }

    public interface CoreView extends View {
    }

    @Inject
    public CorePresenter(final EventBus eventBus, final CoreView view, final CoreProxy proxy,
            final I18nTranslationService i18n) {
        super(eventBus, view, proxy);
    }

    @Override
    protected void revealInParent() {
        RevealRootLayoutContentEvent.fire(eventBus, this);
    }

}
