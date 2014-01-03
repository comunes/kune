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
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.events.client.viewer.CalendarViewer;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class EventRemoveMenuItem.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class EventRemoveMenuItem extends MenuItemDescriptor {

  /**
   * The Class EventEditAction.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class EventEditAction extends CalendarRolAction {

    /** The calendar. */
    private final Provider<CalendarViewer> calendar;

    /** The content service. */
    private final ContentServiceHelper contentService;

    /**
     * Instantiates a new event edit action.
     * 
     * @param res
     *          the res
     * @param calendar
     *          the calendar
     * @param eventBus
     *          the event bus
     * @param session
     *          the session
     * @param i18n
     *          the i18n
     * @param rightsMan
     *          the rights man
     * @param contentService
     *          the content service
     */
    @Inject
    public EventEditAction(final IconicResources res, final Provider<CalendarViewer> calendar,
        final EventBus eventBus, final Session session, final I18nTranslationService i18n,
        final AccessRightsClientManager rightsMan, final ContentServiceHelper contentService) {
      super(eventBus, session, calendar, AccessRolDTO.Administrator, true, true);
      this.calendar = calendar;
      this.contentService = contentService;
      withText(i18n.t("Remove this appointment")).withIcon(res.trashGrey());
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
      contentService.delContent(new StateToken(calendar.get().getAppToEdit().getId()));
    }
  }

  /** The Constant CREATE_APP_ADD_ID. */
  public static final String CREATE_APP_ADD_ID = "event-add-menu-item-add-btn";

  /** The Constant CREATE_APP_CANCEL_ID. */
  public static final String CREATE_APP_CANCEL_ID = "event-add-menu-item-add-btn";

  /** The Constant CREATE_APP_ID. */
  public static final String CREATE_APP_ID = "event-add-menu-item-form";

  /**
   * Instantiates a new event remove menu item.
   * 
   * @param action
   *          the action
   * @param cal
   *          the cal
   */
  @Inject
  public EventRemoveMenuItem(final EventEditAction action, final CalendarOnOverMenu cal) {
    super(action);
    setParent(cal.get());
  }
}
