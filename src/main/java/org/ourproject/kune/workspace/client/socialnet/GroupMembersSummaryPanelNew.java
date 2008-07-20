package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.platf.client.ui.gridmenu.GridItem;
import org.ourproject.kune.platf.client.ui.gridmenu.GridMenuPanel;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

import com.gwtext.client.widgets.ToolbarButton;

public class GroupMembersSummaryPanelNew extends DropDownPanel implements GroupMembersSummaryViewNew {
    private final GridMenuPanel<GroupDTO> gridMenuPanel;

    public GroupMembersSummaryPanelNew(final GroupMembersSummaryPresenterNew presenter,
	    final I18nUITranslationService i18n, final WorkspaceSkeleton ws) {
	super(true);
	super.setHeaderText(i18n.t("Group members"));
	super.setHeaderTitle(i18n.t("People and groups collaborating in this group"));
	gridMenuPanel = new GridMenuPanel<GroupDTO>(i18n.t("This is an orphaned project, if you are interested "
		+ "please request to join to work on it"), true, true, false, true);
	ws.getEntitySummary().addInSummary(this);
	this.setContent(gridMenuPanel);
    }

    public void addAddMemberButton() {
	gridMenuPanel.getBottomBar().addButton(new ToolbarButton("Test"));
    }

    public void addItem(final GridItem<GroupDTO> gridItem) {
	gridMenuPanel.addItem(gridItem);
    }

    public void addJoinButton() {
	gridMenuPanel.getBottomBar().addButton(new ToolbarButton("Test"));
    }

    public void addUnjoinButton() {
	gridMenuPanel.getBottomBar().addButton(new ToolbarButton("Test"));
    }

    public void clear() {
	gridMenuPanel.removeAll();
    }

    public void setDropDownContentVisible(final boolean visible) {
	super.setContentVisible(visible);
    }

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
	super.setTheme(oldTheme, newTheme);
    }

    public void setVisible(final boolean visible) {
	super.setVisible(visible);
    }
}
