package org.ourproject.kune.workspace.client.sitebar.bar;

import org.ourproject.kune.platf.client.dto.StateToken;

public interface SiteBarListener {

    public void onUserLoggedOut();

    public void onChangeState(StateToken token);

}