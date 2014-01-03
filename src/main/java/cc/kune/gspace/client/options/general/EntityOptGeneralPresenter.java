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
package cc.kune.gspace.client.options.general;

import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.events.CurrentEntityChangedEvent;
import cc.kune.gspace.client.options.EntityOptions;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityOptGeneralPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class EntityOptGeneralPresenter {

  /** The entity options. */
  protected final EntityOptions entityOptions;

  /** The event bus. */
  private final EventBus eventBus;

  /** The i18n. */
  protected final I18nUITranslationService i18n;

  /** The session. */
  protected final Session session;

  /** The state manager. */
  protected final StateManager stateManager;

  /** The view. */
  protected EntityOptGeneralView view;

  /**
   * Instantiates a new entity opt general presenter.
   * 
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param eventBus
   *          the event bus
   * @param i18n
   *          the i18n
   * @param entityOptions
   *          the entity options
   */
  public EntityOptGeneralPresenter(final Session session, final StateManager stateManager,
      final EventBus eventBus, final I18nUITranslationService i18n, final EntityOptions entityOptions) {
    this.session = session;
    this.stateManager = stateManager;
    this.eventBus = eventBus;
    this.i18n = i18n;
    this.entityOptions = entityOptions;
  }

  /**
   * Applicable.
   * 
   * @return true, if successful
   */
  protected abstract boolean applicable();

  /**
   * Gets the view.
   * 
   * @return the view
   */
  public IsWidget getView() {
    return view;
  }

  /**
   * Inits the.
   * 
   * @param view
   *          the view
   */
  public void init(final EntityOptGeneralView view) {
    this.view = view;
    setState();
    entityOptions.addTab(view, view.getTabTitle());
    view.setChangeHandler(new ChangeHandler() {
      @Override
      public void onChange(final ChangeEvent event) {
        updateInServer();
        entityOptions.hideMessages();
      }
    });
  }

  /**
   * Reset.
   */
  protected void reset() {
    view.clear();
    entityOptions.hideMessages();
  }

  /**
   * Send change entity event.
   * 
   * @param shortName
   *          the short name
   * @param longName
   *          the long name
   */
  protected void sendChangeEntityEvent(final String shortName, final String longName) {
    CurrentEntityChangedEvent.fire(eventBus, shortName, longName);
  }

  /**
   * Sets the state.
   */
  protected abstract void setState();

  /**
   * Update in server.
   */
  protected abstract void updateInServer();

}
