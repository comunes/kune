package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteusermenu;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.ui.MenuItemCollection;

public interface SiteUserMenuView extends View {

    void setLoggedUserName(String name);

    void setParticipation(MenuItemCollection<GroupDTO> participateInGroups);

    void setVisible(boolean visible);

}
