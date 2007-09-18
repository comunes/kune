package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.SocialNetworkDTO;

public interface SocialNetworkView extends View {

    void setSocialNetwork(SocialNetworkDTO socialNetwork);

    void setPendingCollaboratorsLabelVisible(boolean visible);

    void setVisibleMoreLink(boolean visible);

    void setVisibleJoinLink(boolean visible);

    void setAdminsVisible(boolean visible);

    void setCollabVisible(boolean visible);

}
