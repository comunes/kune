/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
 \*/
package cc.kune.events.client.actions;

import static cc.kune.events.shared.EventsConstants.TYPE_MEETING;
import static cc.kune.events.shared.EventsConstants.TYPE_ROOT;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.actions.AbstractFoldableToolActions;
import cc.kune.gspace.client.actions.ActionGroups;
import cc.kune.gspace.client.actions.ContentViewerOptionsMenu;
import cc.kune.gspace.client.actions.ParticipateInContentBtn;
import cc.kune.gspace.client.actions.RefreshContentMenuItem;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class EventsClientActions extends AbstractFoldableToolActions {
  final String[] all = { TYPE_ROOT, TYPE_MEETING };
  final String[] containers = { TYPE_ROOT };
  final String[] containersNoRoot = {};
  final String[] contents = { TYPE_MEETING };

  @Inject
  public EventsClientActions(final I18nUITranslationService i18n, final Session session,
      final StateManager stateManager, final ActionRegistryByType registry, final CoreResources res,
      final Provider<NewMeetingBtn> newMeetingsBtn, final Provider<GoParentFolderBtn> folderGoUp,
      final Provider<OpenMeetingMenuItem> openContentMenuItem,
      final Provider<DelMeetingMenuItem> delContentMenuItem,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent,
      final Provider<ParticipateInContentBtn> participateBtn,
      final Provider<Calendar1DayViewSelectBtn> cal1DayBtn,
      final Provider<Calendar3DaysViewSelectBtn> cal3DaysBtn,
      final Provider<Calendar7DaysViewSelectBtn> cal7DaysBtn,
      final Provider<CalendarMonthViewSelectBtn> calMonthBtn,
      final Provider<CalendarGoPrevBtn> calPrevBtn, final Provider<CalendarGoNextBtn> calNextBtn,
      final Provider<CalendarGoTodayBtn> goToday, final Provider<RefreshContentMenuItem> refresh) {
    super(session, stateManager, i18n, registry);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, optionsMenuContent, all);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, refresh, all);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, goToday, containers);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, calPrevBtn, containers);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, cal1DayBtn, containers);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, cal3DaysBtn, containers);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, cal7DaysBtn, containers);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, calMonthBtn, containers);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, calNextBtn, containers);

    // For now, commented:
    // actionsRegistry.addAction(ActionGroups.TOOLBAR, newMeetingsBtn,
    // containers);
    // actionsRegistry.addAction(ActionGroups.TOOLBAR, participateBtn,
    // contents);
    // actionsRegistry.addAction(ActionGroups.TOOLBAR, folderGoUp, all);
    // actionsRegistry.addAction(ActionGroups.ITEM_MENU, openContentMenuItem,
    // contents);
    // actionsRegistry.addAction(ActionGroups.ITEM_MENU, openContentMenuItem,
    // containersNoRoot);
    // actionsRegistry.addAction(ActionGroups.ITEM_MENU, delContentMenuItem,
    // contents);

  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
