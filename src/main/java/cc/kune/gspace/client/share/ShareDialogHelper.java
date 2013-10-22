package cc.kune.gspace.client.share;

import static cc.kune.core.shared.dto.GroupListDTO.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cc.kune.core.shared.dto.AccessListsDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.GroupListDTO;

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
    this.localDomain = localDomain;
  }

  public void setState(final GroupDTO currentGroup, final AccessListsDTO acl) {
    setState(currentGroup, acl, NO_MORE_PARTICIPANTS);
  }

  public void setState(final GroupDTO currentGroup, final AccessListsDTO acl,
      final List<String> participants) {
    final GroupListDTO admins = acl.getAdmins();
    final GroupListDTO editors = acl.getEditors();
    final GroupListDTO viewers = acl.getViewers();
    final String editorMode = editors.getMode();
    final String viewerMode = viewers.getMode();
    shareToListView.clear();

    shareToTheNetView.setVisible(viewerMode.equals(EVERYONE));
    shareToOthersView.setVisible(!editorMode.equals(EVERYONE));

    // Admins
    if (admins.getMode().equals(NORMAL)) {
      final Set<GroupDTO> adminList = admins.getList();
      // FIXME
      // if (adminList.contains(currentGroup)) {
      // shareToListView.addOwner(currentGroup);
      // adminList.remove(currentGroup);
      // }
      if (adminList.size() == 1) {
        shareToListView.addOwner(adminList.iterator().next());
      } else {
        for (final GroupDTO group : adminList) {
          shareToListView.addAdmin(group);
        }
      }
    }

    // Editors
    final boolean noEditors = editorMode.equals(NOBODY)
        || (editorMode.equals(NORMAL) && editors.getList().size() == 0 && participants.size() == 0);
    if (noEditors) {
      shareToListView.addNotEditableByOthers();
    } else {
      if (editorMode.equals(NORMAL)) {
        for (final GroupDTO editor : editors.getList()) {
          shareToListView.addEditor(editor);
        }
      } else if (editorMode.equals(EVERYONE)) {
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
        || (viewerMode.equals(NORMAL) && viewers.getList().size() == 0);
    if (noViewers) {
      shareToListView.addNotVisibleByOthers();
    } else {
      if (viewerMode.equals(NORMAL)) {
        for (final GroupDTO viewer : viewers.getList()) {
          shareToListView.addViewer(viewer);
        }
      } else if (viewerMode.equals(EVERYONE)) {
        shareToListView.addVisibleByAnyone();
      }
    }
  }

}
