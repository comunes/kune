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
package cc.kune.core.client.ui.dialogs.tabbed;

import cc.kune.common.client.notify.NotifyLevel;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractTabbedDialogPresenter.
 * 
 * @param <V>
 *          the value type
 * @param <Proxy_>
 *          the generic type
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractTabbedDialogPresenter<V extends View, Proxy_ extends Proxy<?>> extends
    Presenter<View, Proxy<?>> implements AbstractTabbedDialog {

  /**
   * The Interface AbstractTabbedDialogView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface AbstractTabbedDialogView extends View, IsWidget {

    /**
     * Activate tab.
     * 
     * @param index
     *          the index
     */
    void activateTab(int index);

    /**
     * Adds the tab.
     * 
     * @param tab
     *          the tab
     * @param tabTitle
     *          the tab title
     */
    void addTab(IsWidget tab, IsWidget tabTitle);

    /**
     * Destroy.
     */
    void destroy();

    /**
     * Hide.
     */
    void hide();

    /**
     * Hide messages.
     */
    void hideMessages();

    /**
     * Insert tab.
     * 
     * @param tab
     *          the tab
     * @param tabTitle
     *          the tab title
     * @param position
     *          the position
     */
    void insertTab(IsWidget tab, IsWidget tabTitle, int position);

    /**
     * Sets the error message.
     * 
     * @param message
     *          the message
     * @param level
     *          the level
     */
    void setErrorMessage(final String message, final NotifyLevel level);

    /**
     * Show.
     */
    void show();
  }

  /**
   * Instantiates a new abstract tabbed dialog presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   */
  @Inject
  public AbstractTabbedDialogPresenter(final EventBus eventBus, final AbstractTabbedDialogView view,
      final Proxy<?> proxy) {
    super(eventBus, view, proxy);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialog#activateTab(int)
   */
  @Override
  public void activateTab(final int index) {
    getView().activateTab(index);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialog#addTab(com.google
   * .gwt.user.client.ui.IsWidget, com.google.gwt.user.client.ui.IsWidget)
   */
  @Override
  public void addTab(final IsWidget tab, final IsWidget tabTitle) {
    getView().addTab(tab, tabTitle);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.PresenterWidget#getView()
   */
  @Override
  public AbstractTabbedDialogView getView() {
    return (AbstractTabbedDialogView) super.getView();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialog#hide()
   */
  @Override
  public void hide() {
    getView().hide();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialog#hideMessages()
   */
  @Override
  public void hideMessages() {
    getView().hideMessages();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialog#insertTab(com
   * .google.gwt.user.client.ui.IsWidget,
   * com.google.gwt.user.client.ui.IsWidget, int)
   */
  @Override
  public void insertTab(final IsWidget tab, final IsWidget tabTitle, final int index) {
    getView().insertTab(tab, tabTitle, index);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialog#setErrorMessage
   * (java.lang.String, cc.kune.common.client.notify.NotifyLevel)
   */
  @Override
  public void setErrorMessage(final String message, final NotifyLevel level) {
    getView().setErrorMessage(message, level);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialog#show()
   */
  @Override
  public void show() {
    hideMessages();
    getView().show();
  }

}
