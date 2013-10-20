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
 \*/
package cc.kune.wiki.client.actions;

import static cc.kune.gspace.client.actions.ActionGroups.*;
import static cc.kune.wiki.shared.WikiToolConstants.*;
import cc.kune.chat.client.actions.ChatAboutContentBtn;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.registry.NewMenusForTypeIdsRegistry;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.gspace.client.actions.AbstractFoldableToolActions;
import cc.kune.gspace.client.actions.ContentViewerOptionsMenu;
import cc.kune.gspace.client.actions.CopyContentMenuItem;
import cc.kune.gspace.client.actions.MoveContentMenuItem;
import cc.kune.gspace.client.actions.ParticipateInContentBtn;
import cc.kune.gspace.client.actions.PurgeContentBtn;
import cc.kune.gspace.client.actions.PurgeContentMenuItem;
import cc.kune.gspace.client.actions.RefreshContentMenuItem;
import cc.kune.gspace.client.actions.SetAsHomePageMenuItem;
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
import cc.kune.gspace.client.actions.share.ShareSettingsMenuItem;
import cc.kune.trash.shared.TrashToolConstants;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class WikiClientActions extends AbstractFoldableToolActions {
  final String[] all = { TYPE_ROOT, TYPE_FOLDER, TYPE_WIKIPAGE, TYPE_UPLOADEDFILE };
  final String[] containers = { TYPE_ROOT, TYPE_FOLDER };
  final String[] containersNoRoot = { TYPE_FOLDER };
  final String[] contents = { TYPE_WIKIPAGE, TYPE_UPLOADEDFILE };
  final String[] root = { TYPE_ROOT };

  @SuppressWarnings("unchecked")
  @Inject
  public WikiClientActions(final Session session, final ActionRegistryByType registry,
      final CoreResources res, final Provider<GoParentFolderBtn> folderGoUp,
      final Provider<NewWikiMenuItem> newWikiMenuItem, final Provider<NewWikiIconBtn> newWikiIconBtn,
      final Provider<NewFolderMenuItem> newFolderMenuItem,
      final Provider<OpenWikiMenuItem> openContentMenuItem,
      final Provider<DelWikiMenuItem> delContentMenuItem,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent,
      final Provider<ContentViewerShareMenu> shareMenuContent,
      final Provider<ParticipateInContentBtn> participateBtn, final Provider<TutorialBtn> tutorialBtn,
      final Provider<AddAllMembersToContentMenuItem> addAllMenuItem,
      final Provider<AddAdminMembersToContentMenuItem> addAdminMembersMenuItem,
      final Provider<AddCollabMembersToContentMenuItem> addCollabMembersMenuItem,
      final Provider<AddPublicToContentMenuItem> addPublicMenuItem,
      final Provider<PurgeContentMenuItem> purgeMenuItem, final Provider<PurgeContentBtn> purgeBtn,
      final Provider<DelFolderMenuItem> delFolderMenuItem, final Provider<NewFolderBtn> newFolderBtn,
      final Provider<ChatAboutContentBtn> chatAbout, final Provider<RefreshContentMenuItem> refresh,
      final Provider<SetAsHomePageMenuItem> setAsHomePage,
      final NewMenusForTypeIdsRegistry newMenusRegistry, final WikiFolderNewMenu folderNewMenu,
      final Provider<CopyContentMenuItem> copyContent,
      final Provider<WriteToParticipantsMenuItem> writeToParticipants,
      final Provider<MoveContentMenuItem> moveContentMenuItem, final WikiPageNewMenu wikipageNewMenu,
      final Provider<ShareSettingsMenuItem> shareSettings,
      final Provider<ShareInTwitterMenuItem> shareInTwitter,
      final Provider<ShareInGPlusMenuItem> shareInGPlus,
      final Provider<ShareInIdenticaMenuItem> shareInIdentica,
      final Provider<ShareInFacebookMenuItem> shareInFacebook) {
    super(TOOL_NAME, session, registry);
    add(TOPBAR, all, optionsMenuContent);
    add(TOPBAR, all, refresh);
    add(TOPBAR, all, newWikiIconBtn);
    add(TOPBAR, containers, newFolderBtn);
    // add(NAME, TOOLBAR, folderNewMenu,
    // containers);
    add(TOPBAR, contents, wikipageNewMenu);
    // add(NAME, TOOLBAR, newWikiMenuItem,
    // containers);
    // add(NAME, TOOLBAR, newFolderMenuItem,
    // containers);
    add(BOTTOMBAR, contents, folderGoUp);
    add(BOTTOMBAR, containers, folderGoUp);
    add(TOPBAR, all, shareMenuContent);
    add(TOPBAR, all, addAllMenuItem);
    add(TOPBAR, contents, addAdminMembersMenuItem);
    add(TOPBAR, contents, addCollabMembersMenuItem);
    add(TOPBAR, contents, addPublicMenuItem);
    add(TOPBAR, containers, shareInTwitter);
    add(TOPBAR, containers, shareInIdentica);
    add(TOPBAR, containers, shareInGPlus);
    add(TOPBAR, contents, shareSettings);
    // add(TOPBAR,
    // shareInFacebook, all);
    add(TOPBAR, all, tutorialBtn);
    add(TOPBAR, contents, participateBtn);
    add(TOPBAR, contents, chatAbout);
    add(TOPBAR, contents, copyContent);
    add(TOPBAR, contents, writeToParticipants);
    add(ITEM_MENU, contents, openContentMenuItem);
    add(ITEM_MENU, containersNoRoot, openContentMenuItem);
    add(ITEM_MENU, contents, moveContentMenuItem);
    add(ITEM_MENU, containersNoRoot, moveContentMenuItem);
    add(ITEM_MENU, contents, delContentMenuItem);
    add(ITEM_MENU, containersNoRoot, delFolderMenuItem);
    add(ITEM_MENU, contents, setAsHomePage);
    add(ITEM_MENU, contents, addAllMenuItem);
    add(ITEM_MENU, contents, addAdminMembersMenuItem);
    add(ITEM_MENU, contents, addCollabMembersMenuItem);
    add(ITEM_MENU, contents, addPublicMenuItem);
    add(ITEM_MENU, contents, copyContent);
    add(ITEM_MENU, contents, writeToParticipants);
    add(TrashToolConstants.TOOL_NAME, TOPBAR, contents, purgeBtn);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, contents, purgeMenuItem);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, contents, moveContentMenuItem);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, containersNoRoot, moveContentMenuItem);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, contents, moveContentMenuItem);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, containersNoRoot, moveContentMenuItem);
    // Currently new menu in folders has no sense (because we have buttons for
    // the same contents)
    // newMenusRegistry.register(TYPE_FOLDER, folderNewMenu.get());
    // newMenusRegistry.register(TYPE_ROOT, folderNewMenu.get());
    newMenusRegistry.register(TYPE_WIKIPAGE,
        (MenuDescriptor) wikipageNewMenu.get().withText(I18n.t("Add Gadget")));
    newMenusRegistry.register(TYPE_UPLOADEDFILE, wikipageNewMenu.get());
  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
