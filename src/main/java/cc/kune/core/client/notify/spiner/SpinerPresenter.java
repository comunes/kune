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
