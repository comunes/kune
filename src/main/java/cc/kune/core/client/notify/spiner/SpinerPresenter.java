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
package cc.kune.core.client.notify.spiner;

import cc.kune.common.client.notify.ProgressHideEvent;
import cc.kune.common.client.notify.ProgressShowEvent;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.i18n.I18nReadyEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootPopupContentEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class SpinerPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SpinerPresenter extends Presenter<SpinerPresenter.SpinerView, SpinerPresenter.SpinerProxy> {

  /**
   * The Interface SpinerProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface SpinerProxy extends Proxy<SpinerPresenter> {
  }

  /**
   * The Interface SpinerView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface SpinerView extends PopupView {

    /**
     * Fade.
     */
    void fade();

    /**
     * Show.
     * 
     * @param message
     *          the message
     */
    void show(String message);
  }

  /** The i18n. */
  private final I18nTranslationService i18n;

  /**
   * Instantiates a new spiner presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param i18n
   *          the i18n
   */
  @Inject
  public SpinerPresenter(final EventBus eventBus, final SpinerView view, final SpinerProxy proxy,
      final I18nTranslationService i18n) {
    super(eventBus, view, proxy);
    this.i18n = i18n;
    getView().show("");
  }

  /**
   * On i18n ready.
   * 
   * @param event
   *          the event
   */
  @ProxyEvent
  public void onI18nReady(final I18nReadyEvent event) {
    getView().show(i18n.t("Loading"));
  }

  /**
   * On progress hide.
   * 
   * @param event
   *          the event
   */
  @ProxyEvent
  public void onProgressHide(final ProgressHideEvent event) {
    getView().fade();
  }

  /**
   * On progress show.
   * 
   * @param event
   *          the event
   */
  @ProxyEvent
  public void onProgressShow(final ProgressShowEvent event) {
    getView().show(event.getMessage());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    RevealRootPopupContentEvent.fire(this, this);
  }
}
