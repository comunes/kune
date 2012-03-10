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
package cc.kune.wiki.client.actions;

import static cc.kune.wiki.shared.WikiConstants.TYPE_FOLDER;
import static cc.kune.wiki.shared.WikiConstants.TYPE_ROOT;
import static cc.kune.wiki.shared.WikiConstants.TYPE_UPLOADEDFILE;
import static cc.kune.wiki.shared.WikiConstants.TYPE_WIKIPAGE;
import cc.kune.chat.client.actions.ChatAboutContentBtn;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.registry.NewMenusForTypeIdsRegistry;
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
import cc.kune.gspace.client.actions.ParticipateInContentBtn;
import cc.kune.gspace.client.actions.RefreshContentMenuItem;
import cc.kune.gspace.client.actions.SetAsHomePageMenuItem;
import cc.kune.gspace.client.actions.TutorialContainerBtn;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class WikiClientActions extends AbstractFoldableToolActions {
  final String[] all = { TYPE_ROOT, TYPE_FOLDER, TYPE_WIKIPAGE, TYPE_UPLOADEDFILE };
  final String[] containers = { TYPE_ROOT, TYPE_FOLDER };
  final String[] containersNoRoot = { TYPE_FOLDER };
  final String[] contents = { TYPE_WIKIPAGE, TYPE_UPLOADEDFILE };
  final String[] root = { TYPE_ROOT };

  @Inject
  public WikiClientActions(final I18nUITranslationService i18n, final Session session,
      final StateManager stateManager, final ActionRegistryByType registry, final CoreResources res,
      final Provider<GoParentFolderBtn> folderGoUp, final Provider<NewWikiMenuItem> newWikiMenuItem,
      final Provider<NewWikiIconBtn> newWikiIconBtn,
      final Provider<NewFolderMenuItem> newFolderMenuItem,
      final Provider<OpenWikiMenuItem> openContentMenuItem,
      final Provider<DelWikiMenuItem> delContentMenuItem,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent,
      final Provider<ContentViewerShareMenu> shareMenuContent,
      final Provider<ParticipateInContentBtn> participateBtn,
      final Provider<TutorialContainerBtn> tutorialBtn,
      final Provider<AddAllMembersToContentMenuItem> addAllMenuItem,
      final Provider<AddAdminMembersToContentMenuItem> addAdminMembersMenuItem,
      final Provider<AddCollabMembersToContentMenuItem> addCollabMembersMenuItem,
      final Provider<AddPublicToContentMenuItem> addPublicMenuItem,
      final Provider<DelFolderMenuItem> delFolderMenuItem, final Provider<NewFolderBtn> newFolderBtn,
      final Provider<ChatAboutContentBtn> chatAbout, final Provider<RefreshContentMenuItem> refresh,
      final Provider<SetAsHomePageMenuItem> setAsHomePage,
      final NewMenusForTypeIdsRegistry newMenusRegistry, final WikiFolderNewMenu folderNewMenu,
      final WikiPageNewMenu wikipageNewMenu) {
    super(session, stateManager, i18n, registry);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, optionsMenuContent, all);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, refresh, all);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, newWikiIconBtn, all);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, newFolderBtn, containers);
    // actionsRegistry.addAction(ActionGroups.TOOLBAR, folderNewMenu,
    // containers);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, wikipageNewMenu, contents);
    // actionsRegistry.addAction(ActionGroups.TOOLBAR, newWikiMenuItem,
    // containers);
    // actionsRegistry.addAction(ActionGroups.TOOLBAR, newFolderMenuItem,
    // containers);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, folderGoUp, contents);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, folderGoUp, containers);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, shareMenuContent, contents);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, addAllMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, addAdminMembersMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, addCollabMembersMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, addPublicMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, tutorialBtn, containers);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, participateBtn, contents);
    actionsRegistry.addAction(ActionGroups.TOOLBAR, chatAbout, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, openContentMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, openContentMenuItem, containersNoRoot);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, delContentMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, delFolderMenuItem, containersNoRoot);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, setAsHomePage, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, addAllMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, addAdminMembersMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, addCollabMembersMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, addPublicMenuItem, contents);
    // Currently new menu in folders has no sense (because we have buttons for
    // the same contents)
    // newMenusRegistry.register(TYPE_FOLDER, folderNewMenu.get());
    // newMenusRegistry.register(TYPE_ROOT, folderNewMenu.get());
    newMenusRegistry.register(TYPE_WIKIPAGE,
        (MenuDescriptor) wikipageNewMenu.get().withText(i18n.t("Add Gadget")));
    newMenusRegistry.register(TYPE_UPLOADEDFILE, wikipageNewMenu.get());
  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
