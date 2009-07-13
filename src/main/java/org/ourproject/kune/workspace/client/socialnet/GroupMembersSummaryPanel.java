/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 \*/
package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.PlatfMessages;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarView;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.i18n.I18nUITranslationService;
import org.ourproject.kune.platf.client.ui.AbstractToolbar;
import org.ourproject.kune.platf.client.ui.gridmenu.GridDragConfiguration;
import org.ourproject.kune.platf.client.ui.gridmenu.GridItem;
import org.ourproject.kune.platf.client.ui.gridmenu.GridMenuPanel;
import org.ourproject.kune.workspace.client.skel.SummaryPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.emiteuimodule.client.users.UserGridPanel;
import com.calclab.suco.client.events.Listener;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;

public class GroupMembersSummaryPanel extends SummaryPanel implements GroupMembersSummaryView {
    // private static final int MAX_HEIGHT = 110;
    private final GridMenuPanel<GroupDTO> gridMenuPanel;
    private final I18nUITranslationService i18n;
    private final GroupMembersSummaryPresenter presenter;
    private final AbstractToolbar toolbar;
    private final Label noMembersPublic;

    public GroupMembersSummaryPanel(final GroupMembersSummaryPresenter presenter, final I18nUITranslationService i18n,
            final WorkspaceSkeleton ws, final ActionToolbarView<StateToken> actionToolbarView) {
        super(i18n.t("Group members"), i18n.t("People and groups collaborating in this group"), ws);
        this.presenter = presenter;
        this.i18n = i18n;

        final GridDragConfiguration dragConf = new GridDragConfiguration(UserGridPanel.USER_GROUP_DD,
                i18n.t("Drop into the chat area to start a chat.") + "<br/>"
                        + i18n.t("Drop into a room to invite the user to join the chat room"));
        gridMenuPanel = new GridMenuPanel<GroupDTO>(i18n.t("This is an orphaned project, if you are interested "
                + "please request to join to work on it"), dragConf, true, true, false, true, false);
        final Listener<String> go = new Listener<String>() {
            public void onEvent(final String groupShortName) {
                presenter.onDoubleClick(groupShortName);
            }
        };
        // gridMenuPanel.onClick(go);
        gridMenuPanel.onDoubleClick(go);
        gridMenuPanel.getBottomBar().setCls("k-blank-toolbar");
        super.add(gridMenuPanel);

        noMembersPublic = new Label(i18n.t(PlatfMessages.MEMBERS_NOT_PUBLIC));
        noMembersPublic.addStyleName("kune-Margin-7-trbl");
        noMembersPublic.addStyleName("k-text-gray");
        noMembersPublic.setVisible(false);
        super.add(noMembersPublic);

        toolbar = ((ActionToolbarPanel<StateToken>) actionToolbarView).getToolbar();
        toolbar.setBlankStyle();
        super.add((Widget) toolbar);
        super.addInSummary();
        ws.addListenerInEntitySummary(new ContainerListenerAdapter() {
            @Override
            public void onResize(final BoxComponent component, final int adjWidth, final int adjHeight,
                    final int rawWidth, final int rawHeight) {
                gridMenuPanel.setWidth(adjWidth);
            }
        });
    }

    public void addItem(final GridItem<GroupDTO> gridItem) {
        gridMenuPanel.setVisible(true);
        gridMenuPanel.addItem(gridItem);
        doLayoutIfNeeded();
    }

    @Override
    public void clear() {
        gridMenuPanel.removeAll();
        toolbar.removeAll();
        noMembersPublic.setVisible(false);
        doLayoutIfNeeded();
    }

    public void confirmAddCollab(final String groupShortName, final String groupLongName) {
        final String groupName = groupLongName + " (" + groupShortName + ")";
        MessageBox.confirm(i18n.t("Confirm member joining"), i18n.t("Add [%s] as a member?", groupName),
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

    @Override
    public void setDraggable(final boolean draggable) {
        // gridMenuPanel.setDraggable(draggable);
    }

    public void showMembersNotVisible() {
        noMembersPublic.setVisible(true);
        gridMenuPanel.setVisible(false);
        doLayoutIfNeeded();
    }
}
