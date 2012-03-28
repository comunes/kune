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
package cc.kune.blogs.client.actions;

import static cc.kune.blogs.shared.BlogsToolConstants.TYPE_BLOG;
import static cc.kune.blogs.shared.BlogsToolConstants.TYPE_POST;
import static cc.kune.blogs.shared.BlogsToolConstants.TYPE_ROOT;
import static cc.kune.blogs.shared.BlogsToolConstants.TYPE_UPLOADEDFILE;
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
import cc.kune.gspace.client.actions.CopyContentMenuItem;
import cc.kune.gspace.client.actions.ParticipateInContentBtn;
import cc.kune.gspace.client.actions.RefreshContentMenuItem;
import cc.kune.gspace.client.actions.SetAsHomePageMenuItem;
import cc.kune.gspace.client.actions.TutorialContainerBtn;
import cc.kune.gspace.client.actions.WriteToParticipantsMenuItem;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BlogsClientActions extends AbstractFoldableToolActions {

  final String[] all = { TYPE_ROOT, TYPE_BLOG, TYPE_POST, TYPE_UPLOADEDFILE };
  final String[] containers = { TYPE_ROOT, TYPE_BLOG };
  final String[] containersNoRoot = { TYPE_BLOG };
  final String[] contents = { TYPE_POST, TYPE_UPLOADEDFILE };
  final String[] contentsModerated = { TYPE_POST, TYPE_UPLOADEDFILE };
  final String[] noRoot = { TYPE_BLOG, TYPE_POST, TYPE_UPLOADEDFILE };

  @Inject
  public BlogsClientActions(final I18nUITranslationService i18n, final Session session,
      final StateManager stateManager, final ActionRegistryByType registry, final CoreResources res,
      final Provider<GoParentBlogBtn> folderGoUp, final Provider<NewPostMenuItem> newPostItem,
      final Provider<NewPostIconBtn> newPostIconBtn, final Provider<NewBlogBtn> newBlogBtn,
      final Provider<OpenBlogMenuItem> openContentMenuItem,
      final Provider<DelPostMenuItem> delContentMenuItem,
      final Provider<RefreshContentMenuItem> refresh, final Provider<TutorialContainerBtn> tutorialBtn,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent,
      final Provider<ContentViewerShareMenu> shareMenuContent,
      final Provider<AddAllMembersToContentMenuItem> addAllMenuItem,
      final Provider<AddAdminMembersToContentMenuItem> addAdminMembersMenuItem,
      final Provider<AddCollabMembersToContentMenuItem> addCollabMembersMenuItem,
      final Provider<AddPublicToContentMenuItem> addPublicMenuItem,
      final Provider<ParticipateInContentBtn> participateBtn, final BlogsNewMenu blogNewMenu,
      final PostNewMenu postNewMenu, final NewMenusForTypeIdsRegistry newMenusRegistry,
      final Provider<ChatAboutContentBtn> chatAbout, final Provider<DelBlogMenuItem> delFolderMenuItem,
      final Provider<CopyContentMenuItem> copyContent,
      final Provider<WriteToParticipantsMenuItem> writeToParticipants,
      final Provider<SetAsHomePageMenuItem> setAsHomePage) {
    super(session, stateManager, i18n, registry);
    actionsRegistry.addAction(ActionGroups.TOPBAR, optionsMenuContent, all);
    actionsRegistry.addAction(ActionGroups.TOPBAR, newPostIconBtn, noRoot);
    actionsRegistry.addAction(ActionGroups.TOPBAR, blogNewMenu, containersNoRoot);
    actionsRegistry.addAction(ActionGroups.TOPBAR, postNewMenu, contents);
    actionsRegistry.addAction(ActionGroups.TOPBAR, refresh, all);
    actionsRegistry.addAction(ActionGroups.TOPBAR, newPostItem, containersNoRoot);
    actionsRegistry.addAction(ActionGroups.TOPBAR, newBlogBtn, TYPE_ROOT);
    actionsRegistry.addAction(ActionGroups.BOTTOMBAR, folderGoUp, contents);
    actionsRegistry.addAction(ActionGroups.BOTTOMBAR, folderGoUp, containers);
    actionsRegistry.addAction(ActionGroups.TOPBAR, shareMenuContent, contents);
    actionsRegistry.addAction(ActionGroups.TOPBAR, addAllMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.TOPBAR, addAdminMembersMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.TOPBAR, addCollabMembersMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.TOPBAR, addPublicMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.TOPBAR, tutorialBtn, containers);
    actionsRegistry.addAction(ActionGroups.TOPBAR, participateBtn, contents);
    actionsRegistry.addAction(ActionGroups.TOPBAR, chatAbout, contents);
    actionsRegistry.addAction(ActionGroups.TOPBAR, copyContent, contents);
    actionsRegistry.addAction(ActionGroups.TOPBAR, writeToParticipants, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, openContentMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, openContentMenuItem, containersNoRoot);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, delContentMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, delFolderMenuItem, containersNoRoot);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, addAllMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, addAdminMembersMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, addCollabMembersMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, addPublicMenuItem, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, copyContent, contents);
    actionsRegistry.addAction(ActionGroups.ITEM_MENU, writeToParticipants, contents);
    newMenusRegistry.register(TYPE_BLOG, blogNewMenu.get());
    newMenusRegistry.register(TYPE_POST,
        (MenuDescriptor) postNewMenu.get().withText(i18n.t("Add Gadget")));
    newMenusRegistry.register(TYPE_UPLOADEDFILE, postNewMenu.get());
  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
