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
package cc.kune.lists.client.actions;

import static cc.kune.gspace.client.actions.ActionGroups.*;
import static cc.kune.lists.shared.ListsToolConstants.*;
import cc.kune.chat.client.actions.ChatAboutContentBtn;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.actions.ActionRegistryByType;
import cc.kune.core.client.invitation.ListInvitationMenuItem;
import cc.kune.core.client.invitation.ListInvitationShareMenuItem;
import cc.kune.core.client.registry.NewMenusForTypeIdsRegistry;
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
import cc.kune.trash.shared.TrashToolConstants;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ListsClientActions extends AbstractFoldableToolActions {

  final String[] all = { TYPE_ROOT, TYPE_LIST, TYPE_POST };
  final String[] allExceptRoot = { TYPE_LIST, TYPE_POST };
  final String[] containers = { TYPE_ROOT, TYPE_LIST };
  final String[] containersNoRoot = { TYPE_LIST };
  final String[] contents = { TYPE_POST };
  final String[] noRoot = { TYPE_LIST, TYPE_POST };

  @SuppressWarnings("unchecked")
  @Inject
  public ListsClientActions(final Session session, final ActionRegistryByType registry,
      final CoreResources res, final Provider<GoParentFolderBtn> folderGoUp,
      final Provider<NewListPostIconMenuItem> newPostItem,
      final Provider<NewListPostIconBtn> newPostIconBtn,
      final Provider<NewListMenuItem> newListMenuItem, final Provider<NewListBtn> newListBtn,
      final Provider<OpenFolderMenuItem> openContentMenuItem,
      final Provider<RefreshListMenuItem> refreshList, final Provider<SubscribeToListBtn> subscribeBtn,
      final Provider<OptionsListMenu> optionsMenuContainer,
      final Provider<ContentViewerOptionsMenu> optionsMenuContent,
      final Provider<ContentViewerShareMenu> shareMenuContent,
      final Provider<ShowSubscribersOfListBtn> subscribersCount,
      final Provider<TutorialBtn> tutorialBtn,
      final Provider<AddAllMembersToContentMenuItem> addAllMenuItem,
      final Provider<AddAdminMembersToContentMenuItem> addAdminMembersMenuItem,
      final Provider<AddCollabMembersToContentMenuItem> addCollabMembersMenuItem,
      final Provider<AddPublicToContentMenuItem> addPublicMenuItem,
      final Provider<SetListOpenessMenuItem> listOpenessMenuItem,
      final Provider<ParticipateInContentBtn> participateBtn,
      final Provider<CopyContentMenuItem> copyContent,
      final Provider<RefreshContentMenuItem> refreshPost,
      final Provider<PurgeContentMenuItem> purgeMenuItem, final Provider<PurgeContentBtn> purgeBtn,
      final Provider<PurgeContainerMenuItem> purgeFolderMenuItem,
      final Provider<PurgeContainerBtn> purgeFolderBtn,
      final Provider<WriteToParticipantsMenuItem> writeToParticipants, final ListsNewMenu listNewMenu,
      final PostNewMenu postNewMenu, final NewMenusForTypeIdsRegistry newMenusRegistry,
      final Provider<ChatAboutContentBtn> chatAbout, final Provider<DelListMenuItem> delFolderMenuItem,
      final Provider<DelPostMenuItem> delPostMenuItem,
      final Provider<MoveContentMenuItem> moveContentMenuItem,
      final Provider<SetAsHomePageMenuItem> setAsHomePage,
      final Provider<ShareInTwitterMenuItem> shareInTwitter,
      final Provider<ShareInGPlusMenuItem> shareInGPlus,
      final Provider<ShareInIdenticaMenuItem> shareInIdentica,
      final Provider<ShareInFacebookMenuItem> shareInFacebook,
      final Provider<ListInvitationShareMenuItem> shareInvitation,
      final Provider<ListInvitationMenuItem> inviteMenuItem) {
    super(TOOL_NAME, session, registry);
    add(TOPBAR, containers, optionsMenuContainer);
    add(TOPBAR, contents, optionsMenuContent);
    add(TOPBAR, noRoot, newPostIconBtn);
    add(TOPBAR, newListBtn, TYPE_ROOT);
    add(TOPBAR, containersNoRoot, listNewMenu, subscribeBtn, listOpenessMenuItem, newPostItem);
    add(TOPBAR, containers, refreshList);
    add(TOPBAR, contents, postNewMenu, refreshPost);
    // add(NAME, TOOLBAR, newListMenuItem,
    // TYPE_ROOT);
    add(BOTTOMBAR, contents, folderGoUp);
    add(BOTTOMBAR, containers, folderGoUp);
    add(TOPBAR, all, tutorialBtn, shareMenuContent);
    add(TOPBAR, contents, addAllMenuItem, addAdminMembersMenuItem, addCollabMembersMenuItem,
        addPublicMenuItem);
    add(TOPBAR, allExceptRoot, shareInvitation);
    add(TOPBAR, containers, shareInTwitter, shareInIdentica, shareInGPlus);
    // add(TOPBAR,
    // shareInFacebook, all);
    add(TOPBAR, contents, participateBtn);
    add(TOPBAR, containersNoRoot, subscribersCount);
    add(TOPBAR, contents, copyContent, chatAbout, writeToParticipants);
    add(ITEM_MENU, contents, openContentMenuItem);
    add(ITEM_MENU, containersNoRoot, openContentMenuItem, delFolderMenuItem);
    add(ITEM_MENU, allExceptRoot, inviteMenuItem);
    add(ITEM_MENU, contents, delPostMenuItem, addAllMenuItem, addAdminMembersMenuItem,
        addCollabMembersMenuItem, addPublicMenuItem, copyContent, writeToParticipants);
    add(TrashToolConstants.TOOL_NAME, TOPBAR, contents, purgeBtn);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, contents, purgeMenuItem);
    add(TrashToolConstants.TOOL_NAME, TOPBAR, containersNoRoot, purgeFolderBtn);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, containersNoRoot, purgeFolderMenuItem);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, contents, moveContentMenuItem);
    add(TrashToolConstants.TOOL_NAME, ITEM_MENU, containersNoRoot, moveContentMenuItem);
    newMenusRegistry.register(TYPE_LIST, listNewMenu.get());
    newMenusRegistry.register(TYPE_ROOT, listNewMenu.get());
    newMenusRegistry.register(TYPE_POST,
        (MenuDescriptor) postNewMenu.get().withText(I18n.t("Add Gadget")));
  }

  @Override
  protected void createPostSessionInitActions() {
  }
}
