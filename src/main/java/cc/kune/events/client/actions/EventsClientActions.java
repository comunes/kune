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
 \*/
package cc.kune.events.client.actions;

import static cc.kune.events.shared.EventsToolConstants.*;
import static cc.kune.gspace.client.actions.ActionGroups.*;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.gspace.client.actions.AbstractFoldableToolActions;
import cc.kune.gspace.client.actions.ContentViewerOptionsMenu;
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
import cc.kune.gspace.client.actions.share.AddAdminMembersToContentMenuItem;
import cc.kune.gspace.client.actions.share.AddAllMembersToContentMenuItem;
import cc.kune.gspace.client.actions.share.AddCollabMembersToContentMenuItem;
import cc.kune.gspace.client.actions.share.ShareDialogMenuItem;
import cc.kune.gspace.client.actions.share.ShareInHelper;
import cc.kune.gspace.client.actions.share.ShareMenu;
import cc.kune.trash.shared.TrashToolConstants;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class EventsClientActions extends AbstractFoldableToolActions {
  final String[] all = { TYPE_ROOT, TYPE_MEETING };
  final String[] containers = { TYPE_ROOT };
  final String[] containersNoRoot = {};
  final String[] contents = { TYPE_MEETING };

  @SuppressWarnings("unchecked")
  @Inject
  public EventsClientActions(final Session session,

  final ActionRegistryByType registry, final CoreResources res, final Provider<NewEventBtn> newEventBtn,
      final Provider<GoParentFolderBtn> folderGoUp,
      final Provider<OpenMeetingMenuItem> openContentMenuItem,
      final Provider<DelMeetingMenuItem> delContentMenuItem,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent,
      final Provider<ShareMenu> shareMenuContent,
      final Provider<AddAllMembersToContentMenuItem> addAllMenuItem,
      final Provider<AddAdminMembersToContentMenuItem> addAdminMembersMenuItem,
      final Provider<AddCollabMembersToContentMenuItem> addCollabMembersMenuItem,
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
      final Provider<CalendarGoTodayBtn> goToday, final Provider<RefreshContentMenuItem> refresh,
      final Provider<ShareDialogMenuItem> shareSettings, final ShareInHelper shareIHelper) {
    super(TOOL_NAME, session, registry);
    add(TOPBAR, all, optionsMenuContent);
    add(TOPBAR, all, refresh);
    add(TOPBAR, containers, goToday, calPrevBtn, cal1DayBtn, cal3DaysBtn, cal7DaysBtn, calMonthBtn,
        calNextBtn);
    add(TOPBAR, contents, participateBtn);
    add(TOPBAR, all, export);
    add(BOTTOMBAR, contents, folderGoUp);
    add(TOPBAR, all, shareMenuContent);
    add(TOPBAR, contents, addAllMenuItem, addAdminMembersMenuItem, addCollabMembersMenuItem);
    add(TOPBAR, contents, shareIHelper.getShareInWaves());
    add(TOPBAR, all, shareIHelper.getShareInAll());
    add(TOPBAR, contents, shareSettings);
    // add(TOPBAR,
    // shareInFacebook, all);
    add(TOPBAR, all, tutorialBtn);
    add(TOPBAR, contents, copyContent, writeToParticipants);
    // On over calendar menu
    add(TOPBAR, containers, onOverMenu);
    add(TrashToolConstants.TOOL_NAME, TOPBAR, contents, purgeBtn);
    add(TrashToolConstants.TOOL_NAME, TOPBAR, containersNoRoot, purgeFolderBtn);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, contents, purgeMenuItem, moveContentMenuItem);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, containersNoRoot, purgeFolderMenuItem,
        moveContentMenuItem);
    eventOpenMenuItem.get();
    eventAddMenuItem.get();
    eventRemoveMenuItem.get();

    add(TOPBAR, containers, newEventBtn);

    // For now, commented:
    // add(NAME, ITEM_MENU,
    // openContentMenuItem,
    // contents);
    // add(NAME, ITEM_MENU,
    // openContentMenuItem,
    // containersNoRoot);
    // add(NAME, ITEM_MENU,
    // delContentMenuItem,
    // contents);

  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
