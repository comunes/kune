package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign;

import org.ourproject.kune.platf.client.View;

public interface SiteSignInLinkView extends View {

    void setLoggedUserMenuVisible(boolean visible);

    void setLoggedUserName(String shortName, String homePage);

    void setVisibleSignInLink(boolean visible);

}
