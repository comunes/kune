package org.ourproject.kune.sitebar.client.bar;

import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.ui.Image;

public interface SiteBarView extends View {

    void showLoggedUserName(String user);

    void clearUserName();

    void setLogo(Image logo);

    void showProgress(String text);

    void hideProgress();

    void clearSearchText();

    void setSearchText(String text);

    void showLoginDialog();

    void setLogoutLinkVisible(boolean visible);

    public void hideLoginDialog();

    void restoreLoginLink();

    void showNewGroupDialog();

    void hideNewGroupDialog();

}
