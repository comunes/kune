package org.ourproject.kune.workspace.client.socialnet;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.platf.client.ui.gridmenu.GridButton;
import org.ourproject.kune.platf.client.ui.gridmenu.GridDragConfiguration;
import org.ourproject.kune.platf.client.ui.gridmenu.GridItem;
import org.ourproject.kune.platf.client.ui.gridmenu.GridMenuPanel;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.EntitySummary;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

import com.calclab.emiteuimodule.client.users.UserGridPanel;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;

public class GroupMembersSummaryPanel extends DropDownPanel implements GroupMembersSummaryView {
    private final GridMenuPanel<GroupDTO> gridMenuPanel;
    private final I18nUITranslationService i18n;
    private final GroupMembersSummaryPresenter presenter;
    private final HashMap<GridButton, ToolbarButton> buttonsCache;

    public GroupMembersSummaryPanel(final GroupMembersSummaryPresenter presenter, final I18nUITranslationService i18n,
	    final WorkspaceSkeleton ws) {
	super(true);
	this.presenter = presenter;
	this.i18n = i18n;
	super.setHeaderText(i18n.t("Group members"));
	super.setHeaderTitle(i18n.t("People and groups collaborating in this group"));
	super.setBorderStylePrimaryName("k-dropdownouter-members");
	super.addStyleName("kune-Margin-Medium-t");

	final GridDragConfiguration dragConf = new GridDragConfiguration(UserGridPanel.USER_GROUP_DD, i18n
		.t("Drop in the chat area to start a chat.")
		+ "<br/>" + i18n.t("Drop into a room to invite the user to join the chat room"));
	gridMenuPanel = new GridMenuPanel<GroupDTO>(i18n.t("This is an orphaned project, if you are interested "
		+ "please request to join to work on it"), dragConf, true, true, false, true, false);
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
	// Workaround: gwt-ext don't have toolbar.removeItem method ...
	ToolbarButton button = buttonsCache.get(gridButton);
	if (button == null) {
	    button = new ToolbarButton(gridButton.getTitle());
	    button.setIcon(gridButton.getIcon());
	    button.setTooltip(gridButton.getTooltip());
	    button.addListener(new ButtonListenerAdapter() {
		public void onClick(final Button button, final EventObject e) {
		    DeferredCommand.addCommand(new Command() {
			public void execute() {
			    gridButton.getSlot().onEvent("");
			}
		    });
		}
	    });
	    buttonsCache.put(gridButton, button);
	} else {
	    button.setVisible(true);
	}
	gridMenuPanel.getBottomBar().addButton(button);
    }

    public void addItem(final GridItem<GroupDTO> gridItem) {
	gridMenuPanel.addItem(gridItem);
    }

    public void clear() {
	gridMenuPanel.removeAll();
	for (final ToolbarButton button : buttonsCache.values()) {
	    // Workaround: gwt-ext don't have toolbar.removeItem method ...
	    // gridMenuPanel.getBottomBar().getEl().removeChild(button.getElement());
	    button.setVisible(false);
	    button.removeFromParent();
	}
    }

    public void confirmAddCollab(final String groupShortName, final String groupLongName) {
	final String groupName = groupLongName + " (" + groupShortName + ")";
	MessageBox.confirm(i18n.t("Confirm addition of member"), i18n.t("Add [%s] as member?", groupName),
		new MessageBox.ConfirmCallback() {
		    public void execute(final String btnID) {
			if (btnID.equals("yes")) {
			    DeferredCommand.addCommand(new Command() {
				public void execute() {
				    presenter.addCollab(groupShortName);
				}
			    });
			}
		    }
		});
    }

    public void setDraggable(final boolean draggable) {
	// gridMenuPanel.setDraggable(draggable);
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
