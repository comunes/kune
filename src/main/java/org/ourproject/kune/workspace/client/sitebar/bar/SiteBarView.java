package org.ourproject.kune.workspace.client.sitebar.bar;

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LinkDTO;

import com.google.gwt.user.client.ui.Image;

public interface SiteBarView extends View {

    void showLoggedUserName(String name, String homePage);

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

    void setDefaultTextSearch();

    void setGroupsIsMember(List<LinkDTO> groupsIsAdmin, List<LinkDTO> groupsIsCollab);

    void resetOptionsSubmenu();

    void setTextSearchSmall();

    void setTextSearchBig();

    void showSearchPanel(String termToSearch);

    void centerLoginDialog();

    void centerNewGroupDialog();

    void showAlertMessage(String message);

    void mask();

    void mask(String message);

    void unMask();

    void setContentGotoPublicUrl(String publicUrl);

    void setContentPublic(boolean visible);

}
