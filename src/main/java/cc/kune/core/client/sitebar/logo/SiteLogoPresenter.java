package cc.kune.core.client.sitebar.logo;

import cc.kune.core.client.init.AppStartEvent;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class SiteLogoPresenter extends Presenter<SiteLogoPresenter.SiteLogoView, SiteLogoPresenter.SiteLogoProxy> {

    public interface SiteLogoView extends View {
        void setSiteLogoUrl(String siteLogoUrl);
    }

    @ProxyCodeSplit
    public interface SiteLogoProxy extends Proxy<SiteLogoPresenter> {
    }

    @Inject
    public SiteLogoPresenter(EventBus eventBus, SiteLogoView view, SiteLogoProxy proxy) {
        super(eventBus, view, proxy);
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    @ProxyEvent
    public void onAppStart(AppStartEvent event) {
        getView().setSiteLogoUrl(event.getInitData().getSiteLogoUrl());
    }
}