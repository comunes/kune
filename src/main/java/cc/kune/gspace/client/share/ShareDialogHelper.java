package cc.kune.gspace.client.share;

import static cc.kune.core.shared.dto.GroupListDTO.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.GroupListDTO;
import cc.kune.lists.shared.ListsToolConstants;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ShareDialogHelper {

  private static final List<String> NO_MORE_PARTICIPANTS = new ArrayList<String>();
  private String localDomain;
  private final ShareToListView shareToListView;
  private final ShareToOthersView shareToOthersView;
  private final ShareToTheNetView shareToTheNetView;

  @Inject
  public ShareDialogHelper(final ShareToListView shareToListView,
      final ShareToTheNetView shareToTheNetView, final ShareToOthersView shareToOthersView) {
    this.shareToListView = shareToListView;
    this.shareToTheNetView = shareToTheNetView;
    this.shareToOthersView = shareToOthersView;
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

    shareToOthersView.setVisible(!editorMode.equals(EVERYONE) || isAList);

    // Admins
    if (adminsMode.equals(NORMAL)) {
      for (final GroupDTO admin : adminList) {
        if (!admin.equals(currentGroup)) {
          shareToListView.addAdmin(admin);
        }
      }
    }

    // Editors
    final boolean noEditors = editorMode.equals(NOBODY)
        || (editorMode.equals(NORMAL) && editorsList.size() == 0 && participants.size() == 0);
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

    // Participants
    if (participants.contains(localDomain)) {
      shareToListView.addEditableByAnyone();
    }
    for (final String participant : participants) {
      if (!localDomain.equals(participant)) {
        shareToListView.addParticipant(participant);
      }
    }

    // Viewers
    final boolean noViewers = viewerMode.equals(NOBODY)
        || (viewerMode.equals(NORMAL) && viewersList.size() == 0);
    if (noViewers) {
      shareToListView.addNotVisibleByOthers();
    } else {
      if (viewerMode.equals(NORMAL)) {
        for (final GroupDTO viewer : viewersList) {
          if (!viewer.equals(currentGroup)) {
            shareToListView.addViewer(viewer);
          }
        }
      } else if (viewerMode.equals(EVERYONE)) {
        shareToListView.addVisibleByAnyone();
      }
    }
  }

}
