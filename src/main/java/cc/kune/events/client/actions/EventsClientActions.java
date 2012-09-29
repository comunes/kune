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

import static cc.kune.events.shared.EventsToolConstants.TOOL_NAME;
import static cc.kune.events.shared.EventsToolConstants.TYPE_MEETING;
import static cc.kune.events.shared.EventsToolConstants.TYPE_ROOT;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.actions.AbstractFoldableToolActions;
import cc.kune.gspace.client.actions.ActionGroups;
import cc.kune.gspace.client.actions.AddAdminMembersToContentMenuItem;
import cc.kune.gspace.client.actions.AddAllMembersToContentMenuItem;
import cc.kune.gspace.client.actions.AddCollabMembersToContentMenuItem;
import cc.kune.gspace.client.actions.AddPublicToContentMenuItem;
import cc.kune.gspace.client.actions.ContentViewerOptionsMenu;
import cc.kune.gspace.client.actions.ContentViewerShareMenu;
import cc.kune.gspace.client.actions.CopyContentMenuItem;
import cc.kune.gspace.client.actions.MoveContentMenuItem;
import cc.kune.gspace.client.actions.ParticipateInContentBtn;
import cc.kune.gspace.client.actions.PurgeContainerBtn;
import cc.kune.gspace.client.actions.PurgeContainerMenuItem;
import cc.kune.gspace.client.actions.PurgeContentBtn;
import cc.kune.gspace.client.actions.PurgeContentMenuItem;
import cc.kune.gspace.client.actions.RefreshContentMenuItem;
import cc.kune.gspace.client.actions.TutorialBtn;
import cc.kune.gspace.client.actions.WriteToParticipantsMenuItem;
import cc.kune.trash.shared.TrashToolConstants;

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
      final Provider<NewEventBtn> newEventBtn, final Provider<GoParentFolderBtn> folderGoUp,
      final Provider<OpenMeetingMenuItem> openContentMenuItem,
      final Provider<DelMeetingMenuItem> delContentMenuItem,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent,
      final Provider<ContentViewerShareMenu> shareMenuContent,
      final Provider<AddAllMembersToContentMenuItem> addAllMenuItem,
      final Provider<AddAdminMembersToContentMenuItem> addAdminMembersMenuItem,
      final Provider<AddCollabMembersToContentMenuItem> addCollabMembersMenuItem,
      final Provider<AddPublicToContentMenuItem> addPublicMenuItem,
      final Provider<ParticipateInContentBtn> participateBtn,
      final Provider<Calendar1DayViewSelectBtn> cal1DayBtn,
      final Provider<Calendar3DaysViewSelectBtn> cal3DaysBtn,
      final Provider<Calendar7DaysViewSelectBtn> cal7DaysBtn,
      final Provider<CalendarMonthViewSelectBtn> calMonthBtn,
      final Provider<EventAddMenuItem> eventAddMenuItem,
      final Provider<EventOpenMenuItem> eventOpenMenuItem, final Provider<CalendarGoPrevBtn> calPrevBtn,
      final Provider<EventRemoveMenuItem> eventRemoveMenuItem,
      final Provider<CopyContentMenuItem> copyContent, final Provider<TutorialBtn> tutorialBtn,
      final Provider<WriteToParticipantsMenuItem> writeToParticipants,
      final Provider<PurgeContentMenuItem> purgeMenuItem, final Provider<PurgeContentBtn> purgeBtn,
      final Provider<PurgeContainerMenuItem> purgeFolderMenuItem,
      final Provider<MoveContentMenuItem> moveContentMenuItem,
      final Provider<PurgeContainerBtn> purgeFolderBtn, final Provider<ExportCalendarMenuItem> export,
      final Provider<CalendarGoNextBtn> calNextBtn, final CalendarOnOverMenu onOverMenu,
      final Provider<CalendarGoTodayBtn> goToday, final Provider<RefreshContentMenuItem> refresh) {
    super(session, stateManager, i18n, registry);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, optionsMenuContent, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, refresh, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, goToday, containers);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, calPrevBtn, containers);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, cal1DayBtn, containers);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, cal3DaysBtn, containers);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, cal7DaysBtn, containers);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, calMonthBtn, containers);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, calNextBtn, containers);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, participateBtn, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, export, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.BOTTOMBAR, folderGoUp, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, shareMenuContent, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, addAllMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, addAdminMembersMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, addCollabMembersMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, addPublicMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, tutorialBtn, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, copyContent, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, writeToParticipants, contents);
    // On over calendar menu
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, onOverMenu, containers);
    actionsRegistry.addAction(TrashToolConstants.TOOL_NAME, ActionGroups.TOPBAR, purgeBtn, contents);
    actionsRegistry.addAction(TrashToolConstants.TOOL_NAME, ActionGroups.ITEM_MENU, purgeMenuItem,
        contents);
    actionsRegistry.addAction(TrashToolConstants.TOOL_NAME, ActionGroups.TOPBAR, purgeFolderBtn,
        containersNoRoot);
    actionsRegistry.addAction(TrashToolConstants.TOOL_NAME, ActionGroups.ITEM_MENU, purgeFolderMenuItem,
        containersNoRoot);
    actionsRegistry.addAction(TrashToolConstants.TOOL_NAME, ActionGroups.ITEM_MENU, moveContentMenuItem,
        contents);
    actionsRegistry.addAction(TrashToolConstants.TOOL_NAME, ActionGroups.ITEM_MENU, moveContentMenuItem,
        containersNoRoot);
    eventOpenMenuItem.get();
    eventAddMenuItem.get();
    eventRemoveMenuItem.get();

    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, newEventBtn, containers);

    // For now, commented:
    // actionsRegistry.addAction(NAME, ActionGroups.ITEM_MENU,
    // openContentMenuItem,
    // contents);
    // actionsRegistry.addAction(NAME, ActionGroups.ITEM_MENU,
    // openContentMenuItem,
    // containersNoRoot);
    // actionsRegistry.addAction(NAME, ActionGroups.ITEM_MENU,
    // delContentMenuItem,
    // contents);

  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
