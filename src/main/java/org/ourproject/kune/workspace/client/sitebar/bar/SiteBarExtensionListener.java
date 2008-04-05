package org.ourproject.kune.workspace.client.sitebar.bar;

import org.ourproject.kune.platf.client.dto.UserInfoDTO;

public interface SiteBarExtensionListener {
    public void onUserLoggedIn(UserInfoDTO user);

    public void onUserLoggedOut();
}