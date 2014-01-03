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

import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.actions.RolActionHelper;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.events.client.viewer.CalendarStateChangeEvent;
import cc.kune.events.client.viewer.CalendarViewer;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class CalendarRolAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class CalendarRolAction extends RolAction {

  /**
   * Instantiates a new calendar rol action.
   * 
   * @param eventBus
   *          the event bus
   * @param session
   *          the session
   * @param calendar
   *          the calendar
   * @param rolRequired
   *          the rol required
   * @param authNeed
   *          the auth need
   * @param onlyOnApp
   *          the only on app
   */
  public CalendarRolAction(final EventBus eventBus, final Session session,
      final Provider<CalendarViewer> calendar, final AccessRolDTO rolRequired, final boolean authNeed,
      final boolean onlyOnApp) {
    super(rolRequired, authNeed);
    eventBus.addHandler(CalendarStateChangeEvent.getType(),
        new CalendarStateChangeEvent.CalendarStateChangeHandler() {
          @Override
          public void onCalendarStateChange(final CalendarStateChangeEvent event) {
            // if the calendar is not selecting a appointment don't show this
            final AccessRights rights = session.getContainerState().getContainerRights();
            final boolean isEnabled = RolActionHelper.isAuthorized(rolRequired, rights);
            final boolean isMember = RolActionHelper.isMember(rights);
            final boolean isOnApp = !calendar.get().getAppToEdit().equals(CalendarViewer.NO_APPOINT);
            final boolean newEnabled = isMember && isEnabled && (!onlyOnApp || isOnApp);
            setEnabled(!newEnabled);
            setEnabled(newEnabled);
          }
        });
  }

}
