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
package cc.kune.events.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.events.client.viewer.CalendarViewer;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class EventOpenMenuItem.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class EventOpenMenuItem extends MenuItemDescriptor {

  /**
   * The Class EventEditAction.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class EventEditAction extends CalendarRolAction {

    /** The calendar. */
    private final Provider<CalendarViewer> calendar;

    /** The state manager. */
    private final StateManager stateManager;

    /**
     * Instantiates a new event edit action.
     * 
     * @param res
     *          the res
     * @param i18n
     *          the i18n
     * @param calendar
     *          the calendar
     * @param eventBus
     *          the event bus
     * @param stateManager
     *          the state manager
     * @param session
     *          the session
     * @param rightsMan
     *          the rights man
     */
    @Inject
    public EventEditAction(final IconicResources res, final I18nTranslationService i18n,
        final Provider<CalendarViewer> calendar, final EventBus eventBus,
        final StateManager stateManager, final Session session, final AccessRightsClientManager rightsMan) {
      super(eventBus, session, calendar, AccessRolDTO.Viewer, false, true);
      this.calendar = calendar;
      this.stateManager = stateManager;
      withText(i18n.t("Open the appointment")).withIcon(res.rightArrow());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.
     * common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      stateManager.gotoHistoryToken(calendar.get().getAppToEdit().getId());
    }
  }

  /**
   * Instantiates a new event open menu item.
   * 
   * @param action
   *          the action
   * @param cal
   *          the cal
   */
  @Inject
  public EventOpenMenuItem(final EventEditAction action, final CalendarOnOverMenu cal) {
    super(action);
    setParent(cal.get());
  }
}
