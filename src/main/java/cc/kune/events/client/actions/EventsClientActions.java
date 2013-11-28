/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.actions.AbstractFoldableToolActions;
import cc.kune.gspace.client.actions.ActionGroups;
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
import cc.kune.gspace.client.actions.share.AddPublicToContentMenuItem;
import cc.kune.gspace.client.actions.share.ContentViewerShareMenu;
import cc.kune.gspace.client.actions.share.ShareInFacebookMenuItem;
import cc.kune.gspace.client.actions.share.ShareInGPlusMenuItem;
import cc.kune.gspace.client.actions.share.ShareInIdenticaMenuItem;
import cc.kune.gspace.client.actions.share.ShareInTwitterMenuItem;
import cc.kune.trash.shared.TrashToolConstants;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class EventsClientActions.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EventsClientActions extends AbstractFoldableToolActions {

  /** The all. */
  final String[] all = { TYPE_ROOT, TYPE_MEETING };

  /** The containers. */
  final String[] containers = { TYPE_ROOT };

  /** The containers no root. */
  final String[] containersNoRoot = {};

  /** The contents. */
  final String[] contents = { TYPE_MEETING };

  /**
   * Instantiates a new events client actions.
   * 
   * @param i18n
   *          the i18n
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param registry
   *          the registry
   * @param res
   *          the res
   * @param newEventBtn
   *          the new event btn
   * @param folderGoUp
   *          the folder go up
   * @param openContentMenuItem
   *          the open content menu item
   * @param delContentMenuItem
   *          the del content menu item
   * @param optionsMenuContent
   *          the options menu content
   * @param shareMenuContent
   *          the share menu content
   * @param addAllMenuItem
   *          the add all menu item
   * @param addAdminMembersMenuItem
   *          the add admin members menu item
   * @param addCollabMembersMenuItem
   *          the add collab members menu item
   * @param addPublicMenuItem
   *          the add public menu item
   * @param participateBtn
   *          the participate btn
   * @param cal1DayBtn
   *          the cal1 day btn
   * @param cal3DaysBtn
   *          the cal3 days btn
   * @param cal7DaysBtn
   *          the cal7 days btn
   * @param calMonthBtn
   *          the cal month btn
   * @param eventAddMenuItem
   *          the event add menu item
   * @param eventOpenMenuItem
   *          the event open menu item
   * @param calPrevBtn
   *          the cal prev btn
   * @param eventRemoveMenuItem
   *          the event remove menu item
   * @param copyContent
   *          the copy content
   * @param tutorialBtn
   *          the tutorial btn
   * @param writeToParticipants
   *          the write to participants
   * @param purgeMenuItem
   *          the purge menu item
   * @param purgeBtn
   *          the purge btn
   * @param purgeFolderMenuItem
   *          the purge folder menu item
   * @param moveContentMenuItem
   *          the move content menu item
   * @param purgeFolderBtn
   *          the purge folder btn
   * @param export
   *          the export
   * @param calNextBtn
   *          the cal next btn
   * @param onOverMenu
   *          the on over menu
   * @param goToday
   *          the go today
   * @param refresh
   *          the refresh
   * @param shareInTwitter
   *          the share in twitter
   * @param shareInGPlus
   *          the share in g plus
   * @param shareInIdentica
   *          the share in identica
   * @param shareInFacebook
   *          the share in facebook
   */
  @Inject
  public EventsClientActions(final I18nTranslationService i18n, final Session session,
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
      final Provider<CalendarGoTodayBtn> goToday, final Provider<RefreshContentMenuItem> refresh,
      final Provider<ShareInTwitterMenuItem> shareInTwitter,
      final Provider<ShareInGPlusMenuItem> shareInGPlus,
      final Provider<ShareInIdenticaMenuItem> shareInIdentica,
      final Provider<ShareInFacebookMenuItem> shareInFacebook) {
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
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, shareMenuContent, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, addAllMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, addAdminMembersMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, addCollabMembersMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, addPublicMenuItem, contents);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, shareInTwitter, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, shareInIdentica, all);
    actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR, shareInGPlus, all);
    // actionsRegistry.addAction(TOOL_NAME, ActionGroups.TOPBAR,
    // shareInFacebook, all);
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.actions.AbstractFoldableToolActions#
   * createPostSessionInitActions()
   */
  @Override
  protected void createPostSessionInitActions() {
  }
}
