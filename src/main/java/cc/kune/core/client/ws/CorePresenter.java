/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

import cc.kune.common.shared.i18n.I18nTranslationService;
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

// TODO: Auto-generated Javadoc
/**
 * The Class CorePresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class CorePresenter extends Presenter<CorePresenter.CoreView, CorePresenter.CoreProxy> {

  /**
   * The Interface CoreProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyStandard
  public interface CoreProxy extends Proxy<CorePresenter> {
  }

  /**
   * The Interface CoreView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface CoreView extends View {
  }

  /**
   * Instantiates a new core presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param appStarter
   *          the app starter
   * @param i18n
   *          the i18n
   */
  @Inject
  public CorePresenter(final EventBus eventBus, final CoreView view, final CoreProxy proxy,
      final AppStarter appStarter, final I18nTranslationService i18n) {
    super(eventBus, view, proxy);
    if (((I18nUITranslationService) i18n).isReady()) {
      appStarter.start();
    }

    eventBus.addHandler(I18nReadyEvent.getType(), new I18nReadyEvent.I18nReadyHandler() {
      @Override
      public void onI18nReady(final I18nReadyEvent event) {
        appStarter.start();
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    RevealRootLayoutContentEvent.fire(this, this);
  }

}
