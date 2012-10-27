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

@Singleton
public class EventOpenMenuItem extends MenuItemDescriptor {
  public static class EventEditAction extends CalendarRolAction {
    private final Provider<CalendarViewer> calendar;
    private final StateManager stateManager;

    @Inject
    public EventEditAction(final IconicResources res, final I18nTranslationService i18n,
        final Provider<CalendarViewer> calendar, final EventBus eventBus,
        final StateManager stateManager, final Session session, final AccessRightsClientManager rightsMan) {
      super(eventBus, session, calendar, AccessRolDTO.Viewer, false, true);
      this.calendar = calendar;
      this.stateManager = stateManager;
      withText(i18n.t("Open the appointment")).withIcon(res.rightArrow());
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      stateManager.gotoHistoryToken(calendar.get().getAppToEdit().getId());
    }
  }

  @Inject
  public EventOpenMenuItem(final EventEditAction action, final CalendarOnOverMenu cal) {
    super(action);
    setParent(cal.get());
  }
}
