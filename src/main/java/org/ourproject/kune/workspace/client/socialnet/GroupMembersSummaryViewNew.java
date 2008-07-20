package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.ui.gridmenu.GridItem;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

public interface GroupMembersSummaryViewNew {

    void addAddMemberButton();

    void addItem(GridItem<GroupDTO> gridItem);

    void addJoinButton();

    void addUnjoinButton();

    void clear();

    void setDropDownContentVisible(boolean visible);

    void setTheme(WsTheme oldTheme, WsTheme newTheme);

    void setVisible(boolean visible);

}
