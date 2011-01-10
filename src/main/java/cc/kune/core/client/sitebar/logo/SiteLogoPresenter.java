package cc.kune.core.client.sitebar.logo;

import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.ws.CorePlaceManager;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class SiteLogoPresenter extends Presenter<SiteLogoPresenter.SiteLogoView, SiteLogoPresenter.SiteLogoProxy>
        implements SiteLogoUiHandlers {

    private final CorePlaceManager placeManager;

    public interface SiteLogoView extends View, HasUiHandlers<SiteLogoUiHandlers> {
        void setSiteLogoUrl(String siteLogoUrl);
    }

    @ProxyCodeSplit
    public interface SiteLogoProxy extends Proxy<SiteLogoPresenter> {
    }

    @Inject
    public SiteLogoPresenter(EventBus eventBus, SiteLogoView view, SiteLogoProxy proxy, CorePlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    @ProxyEvent
    public void onAppStart(AppStartEvent event) {
        getView().setSiteLogoUrl(event.getInitData().getSiteLogoUrl());
    }

    @Override
    public void onClick() {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                // placeManager.revealDefaultPlace();
            }
        });
    }
}