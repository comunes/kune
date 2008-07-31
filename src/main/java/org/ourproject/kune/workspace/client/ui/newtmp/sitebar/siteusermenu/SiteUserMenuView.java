package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteusermenu;

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LinkDTO;

public interface SiteUserMenuView extends View {

    void setLoggedUserName(String name);

    void setUseGroupsIsMember(List<LinkDTO> groupsIsAdmin, List<LinkDTO> groupsIsCollab);

    void setVisible(boolean visible);

}
