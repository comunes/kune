/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
 */
package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarView;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.gridmenu.GridItem;
import org.ourproject.kune.platf.client.ui.gridmenu.GridMenuPanel;
import org.ourproject.kune.workspace.client.skel.SimpleToolbar;
import org.ourproject.kune.workspace.client.skel.SummaryPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsTheme;

import com.calclab.suco.client.listener.Listener;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;

public class ParticipationSummaryPanel extends SummaryPanel implements ParticipationSummaryView {

    private final GridMenuPanel<GroupDTO> gridMenuPanel;
    private final SimpleToolbar toolbar;

    public ParticipationSummaryPanel(final ParticipationSummaryPresenter presenter, final I18nTranslationService i18n,
            final WorkspaceSkeleton ws, ActionToolbarView<StateToken> actionToolbarView) {
        super(i18n.t("Participates in"), i18n.t("Groups in which participates"), ws);
        gridMenuPanel = new GridMenuPanel<GroupDTO>(i18n.t("This user is not member of any group"), false, false,
                false, false, false);
        Listener<String> go = new Listener<String>() {
            public void onEvent(final String groupShortName) {
                presenter.onDoubleClick(groupShortName);
            }
        };
        // gridMenuPanel.onClick(go);
        gridMenuPanel.onDoubleClick(go);
        super.add(gridMenuPanel);
        toolbar = ((ActionToolbarPanel<StateToken>) actionToolbarView).getToolbar(ActionToolbarPosition.bottombar);
        toolbar.setCleanStyle();
        super.add(toolbar);
        addInSummary();
        ws.addListenerInEntitySummary(new ContainerListenerAdapter() {
            @Override
            public void onResize(final BoxComponent component, final int adjWidth, final int adjHeight,
                    final int rawWidth, final int rawHeight) {
                gridMenuPanel.setWidth(adjWidth);
            }
        });
    }

    public void addItem(final GridItem<GroupDTO> gridItem) {
        gridMenuPanel.addItem(gridItem);
    }

    @Override
    public void clear() {
        gridMenuPanel.removeAll();
        toolbar.removeAll();
    }

    public void setTheme(WsTheme oldTheme, WsTheme newTheme) {
        // TODO Auto-generated method stub
    }

}
