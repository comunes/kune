package org.ourproject.kune.workspace.client.socialnet;

import java.util.List;

import org.ourproject.kune.platf.client.View;

public interface SocialNetworkView extends View {

    void setVisibleJoinLink(boolean visible);

    void setVisibleAddMemberLink(boolean visible);

    void addAdminsItems(int numAdmins, List adminsList, boolean userIsAdmin);

    void addCollabItems(int numCollaborators, List collabList, boolean userIsAdmin);

    void addPendingCollabsItems(int numPendingCollabs, List groupList, boolean userIsAdmin);

    void clearGroups();

    void setDropDownContentVisible(boolean visible);

}
