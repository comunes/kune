/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.core.client.sitebar.logo;

import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.state.SiteCommonTokens;
import cc.kune.core.client.state.StateManager;

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

    @ProxyCodeSplit
    public interface SiteLogoProxy extends Proxy<SiteLogoPresenter> {
    }

    public interface SiteLogoView extends View, HasUiHandlers<SiteLogoUiHandlers> {
        void setSiteLogoUrl(String siteLogoUrl);
    }

    private final StateManager stateManager;

    @Inject
    public SiteLogoPresenter(final EventBus eventBus, final SiteLogoView view, final SiteLogoProxy proxy,
            final StateManager stateManager) {
        super(eventBus, view, proxy);
        this.stateManager = stateManager;
        getView().setUiHandlers(this);
    }

    @ProxyEvent
    public void onAppStart(final AppStartEvent event) {
        getView().setSiteLogoUrl(event.getInitData().getSiteLogoUrl());
    }

    @Override
    public void onClick() {
        stateManager.gotoToken(SiteCommonTokens.GROUP_HOME);
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }
}