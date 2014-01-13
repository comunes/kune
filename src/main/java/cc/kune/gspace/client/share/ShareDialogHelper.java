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
package cc.kune.gspace.client.share;

import static cc.kune.core.shared.dto.GroupListDTO.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.GroupListDTO;
import cc.kune.gspace.client.actions.share.ContentViewerShareMenu;
import cc.kune.lists.shared.ListsToolConstants;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ShareDialogHelper {

  private static final List<String> NO_MORE_PARTICIPANTS = new ArrayList<String>();
  private String localDomain;
  ContentViewerShareMenu shareMenuBtn;
  private final ShareToListView shareToListView;
  private final ShareToOthersView shareToOthersView;
  private final ShareToTheNetView shareToTheNetView;

  @Inject
  public ShareDialogHelper(final ShareToListView shareToListView,
      final ShareToTheNetView shareToTheNetView, final ShareToOthersView shareToOthersView,
      final ContentViewerShareMenu shareMenuBtn) {
    this.shareToListView = shareToListView;
    this.shareToTheNetView = shareToTheNetView;
    this.shareToOthersView = shareToOthersView;
    this.shareMenuBtn = shareMenuBtn;
  }

  public void init(final String localDomain) {
    this.localDomain = "@" + localDomain;
  }

  public void setState(final GroupDTO currentGroup, final AccessListsDTO acl, final String typeId) {
    setState(currentGroup, acl, typeId, NO_MORE_PARTICIPANTS);
  }

  public void setState(final GroupDTO currentGroup, final AccessListsDTO acl, final String typeId,
      final List<String> participants) {
    final GroupListDTO admins = acl.getAdmins();
    final GroupListDTO editors = acl.getEditors();
    final GroupListDTO viewers = acl.getViewers();
    final String adminsMode = admins.getMode();
    final String editorMode = editors.getMode();
    final String viewerMode = viewers.getMode();
    final Set<GroupDTO> adminList = admins.getList();
    final Set<GroupDTO> editorsList = editors.getList();
    final Set<GroupDTO> viewersList = viewers.getList();
    shareToListView.clear();

    // Owner
    shareToListView.addOwner(currentGroup);

    shareToTheNetView.setVisible(viewerMode.equals(EVERYONE));
    final boolean isAList = typeId.equals(ListsToolConstants.TYPE_LIST);
    final boolean isWave = participants.size() != 0;

    shareToOthersView.setVisible(!editorMode.equals(EVERYONE) || isAList);

    // Admins
    if (!isWave && adminsMode.equals(NORMAL)) {
      for (final GroupDTO admin : adminList) {
        if (!admin.equals(currentGroup)) {
          shareToListView.addAdmin(admin);
        }
      }
    }
    if (isWave) {
      // Participants
      if (participants.contains(localDomain)) {
        shareToListView.addEditableByAnyone();
      }
      for (final String participant : participants) {
        if (!localDomain.equals(participant)) {
          shareToListView.addParticipant(participant);
        }
      }
    } else {
      // Editors
      final boolean noEditors = editorMode.equals(NOBODY)
          || (editorMode.equals(NORMAL) && editorsList.size() == 0);
      if (noEditors && !isAList) {
        shareToListView.addNotEditableByOthers();
      } else {
        if (editorMode.equals(NORMAL)) {
          for (final GroupDTO editor : editorsList) {
            if (!editor.equals(currentGroup)) {
              shareToListView.addEditor(editor);
            }
          }
        } else if (editorMode.equals(EVERYONE) && !isAList) {
          shareToListView.addEditableByAnyone();
        }
      }
    }
    // Viewers
    final boolean noViewers = viewerMode.equals(NOBODY)
        || (viewerMode.equals(NORMAL) && viewersList.size() == 0);
    if (noViewers) {
      shareToListView.addNotVisibleByOthers();
      shareMenuBtn.setVisibleToEveryone(false);
    } else {
      if (viewerMode.equals(NORMAL)) {
        for (final GroupDTO viewer : viewersList) {
          if (!viewer.equals(currentGroup)) {
            shareToListView.addViewer(viewer);
          }
        }
        shareMenuBtn.setVisibleToEveryone(false);
      } else if (viewerMode.equals(EVERYONE)) {
        shareToListView.addVisibleByAnyone();
        shareMenuBtn.setVisibleToEveryone(true);
      }
    }
  }
}
