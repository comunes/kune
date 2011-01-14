package cc.kune.core.client.notify.spiner;

import cc.kune.core.client.i18n.I18nReadyEvent;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootPopupContentEvent;

public class SpinerPresenter extends Presenter<SpinerPresenter.SpinerView, SpinerPresenter.SpinerProxy> {

    private final I18nTranslationService i18n;

    @ProxyCodeSplit
    public interface SpinerProxy extends Proxy<SpinerPresenter> {
    }

    public interface SpinerView extends PopupView {
        void fade();

        void show(String message);
    }

    @Inject
    public SpinerPresenter(final EventBus eventBus, final SpinerView view, final SpinerProxy proxy,
            I18nTranslationService i18n) {
        super(eventBus, view, proxy);
        this.i18n = i18n;
    }

    @ProxyEvent
    public void onProgressShow(ProgressShowEvent event) {
        getView().show(event.getMessage());
    }

    @ProxyEvent
    public void onI18nReady(I18nReadyEvent event) {
        getView().show(i18n.t("Loading"));
    }

    @ProxyEvent
    public void onProgressHide(ProgressHideEvent event) {
        getView().fade();
    }

    @Override
    protected void revealInParent() {
        RevealRootPopupContentEvent.fire(this, this);
    }
}
