/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.ws;

import cc.kune.core.client.i18n.I18nReadyEvent;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.init.AppStarter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

/**
 * The Class CorePresenter.
 */
@Singleton
public class CorePresenter extends Presenter<CorePresenter.CoreView, CorePresenter.CoreProxy> {
  @ProxyStandard
  public interface CoreProxy extends Proxy<CorePresenter> {
  }
  public interface CoreView extends View {
  }

  @Inject
  public CorePresenter(final EventBus eventBus, final CoreView view, final CoreProxy proxy,
      final AppStarter appStarter, final I18nUITranslationService i18n) {
    super(eventBus, view, proxy);
    if (i18n.isReady()) {
      appStarter.start();
    }

    eventBus.addHandler(I18nReadyEvent.getType(), new I18nReadyEvent.I18nReadyHandler() {
      @Override
      public void onI18nReady(final I18nReadyEvent event) {
        appStarter.start();
      }
    });
  }

  @Override
  protected void revealInParent() {
    RevealRootLayoutContentEvent.fire(this, this);
  }

}
