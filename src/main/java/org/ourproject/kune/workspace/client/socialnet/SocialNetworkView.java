package org.ourproject.kune.workspace.client.socialnet;

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;

public interface SocialNetworkView extends View {

    void setVisibleJoinLink(boolean visible);

    void setVisibleAddMemberLink(boolean visible);

    void addAdminsItems(int numAdmins, List adminsList, AccessRightsDTO rights);

    void addCollabItems(int numCollaborators, List collabList, AccessRightsDTO rights);

    void addPendingCollabsItems(int numPendingCollabs, List groupList, AccessRightsDTO rights);

    void clearGroups();

    void setDropDownContentVisible(boolean visible);

}
