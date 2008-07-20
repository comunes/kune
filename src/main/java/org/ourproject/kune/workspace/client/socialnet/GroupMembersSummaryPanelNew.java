package org.ourproject.kune.workspace.client.socialnet;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.platf.client.ui.gridmenu.GridButton;
import org.ourproject.kune.platf.client.ui.gridmenu.GridItem;
import org.ourproject.kune.platf.client.ui.gridmenu.GridMenuPanel;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.EntitySummary;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;

public class GroupMembersSummaryPanelNew extends DropDownPanel implements GroupMembersSummaryViewNew {
    private final GridMenuPanel<GroupDTO> gridMenuPanel;
    private final I18nUITranslationService i18n;
    private final GroupMembersSummaryPresenterNew presenter;
    private final HashMap<GridButton, ToolbarButton> buttonsCache;

    public GroupMembersSummaryPanelNew(final GroupMembersSummaryPresenterNew presenter,
	    final I18nUITranslationService i18n, final WorkspaceSkeleton ws) {
	super(true);
	this.presenter = presenter;
	this.i18n = i18n;
	super.setHeaderText(i18n.t("Group members"));
	super.setHeaderTitle(i18n.t("People and groups collaborating in this group"));
	super.setBorderStylePrimaryName("k-dropdownouter-members");
	super.addStyleName("kune-Margin-Medium-tl");
	gridMenuPanel = new GridMenuPanel<GroupDTO>(i18n.t("This is an orphaned project, if you are interested "
		+ "please request to join to work on it"), true, true, false, true, false);
	final EntitySummary entitySummary = ws.getEntitySummary();
	entitySummary.addInSummary(this);
	entitySummary.addListener(new ContainerListenerAdapter() {
	    @Override
	    public void onResize(final BoxComponent component, final int adjWidth, final int adjHeight,
		    final int rawWidth, final int rawHeight) {
		gridMenuPanel.setWidth(adjWidth);
	    }
	});
	this.setContent(gridMenuPanel);
	buttonsCache = new HashMap<GridButton, ToolbarButton>();
    }

    public void addButton(final GridButton gridButton) {
	ToolbarButton button = buttonsCache.get(gridButton);
	if (button == null) {
	    button = new ToolbarButton(gridButton.getTitle());
	    button.setIcon(gridButton.getIcon());
	    button.setTooltip(gridButton.getTooltip());
	    button.addListener(new ButtonListenerAdapter() {
		public void onClick(final Button button, final EventObject e) {
		    gridButton.getSlot().onEvent("");
		}
	    });
	}
	if (!button.isAttached()) {
	    gridMenuPanel.getBottomBar().addButton(button);
	}
    }

    public void addItem(final GridItem<GroupDTO> gridItem) {
	gridMenuPanel.addItem(gridItem);
    }

    public void clear() {
	gridMenuPanel.removeAll();
	for (final ToolbarButton button : buttonsCache.values()) {
	    button.removeFromParent();
	}
    }

    public void confirmAddCollab(final String groupShortName, final String groupLongName) {
	final String groupName = groupLongName + " (" + groupShortName + ")";
	MessageBox.confirm(i18n.t("Confirm addition of member"), i18n.t("Add [%s] as member?", groupName),
		new MessageBox.ConfirmCallback() {
		    public void execute(final String btnID) {
			if (btnID.equals("yes")) {
			    presenter.addCollab(groupShortName);
			}
		    }
		});
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
