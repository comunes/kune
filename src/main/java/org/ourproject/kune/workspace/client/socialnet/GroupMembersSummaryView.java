package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.ui.gridmenu.GridButton;
import org.ourproject.kune.platf.client.ui.gridmenu.GridItem;
import org.ourproject.kune.workspace.client.themes.WsTheme;

public interface GroupMembersSummaryView {

    void addButton(GridButton gridButton);

    void addItem(GridItem<GroupDTO> gridItem);

    void addToolbarFill();

    void clear();

    void confirmAddCollab(String groupShortName, String groupLongName);

    void setContentVisible(boolean visible);

    void setDefaultHeigth();

    void setDraggable(boolean draggable);

    void setMaxHeigth();

    void setTheme(WsTheme oldTheme, WsTheme newTheme);

    void setVisible(boolean visible);

}
