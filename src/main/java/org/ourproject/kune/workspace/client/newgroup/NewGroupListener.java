package org.ourproject.kune.workspace.client.newgroup;

import org.ourproject.kune.platf.client.dto.StateToken;

public interface NewGroupListener {

    void onNewGroupCancel();

    void onNewGroupClose();

    void onNewGroupCreated(StateToken homePage);

}
