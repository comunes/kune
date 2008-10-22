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
package org.ourproject.kune.platf.client.tool;

import java.util.HashMap;
import java.util.List;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;
import com.calclab.suco.client.listener.Listener2;

public class ToolSelectorPresenter implements ToolSelector {

    private final HashMap<String, ToolSelectorItem> tools;

    public ToolSelectorPresenter(final StateManager stateManager, final WsThemePresenter wsThemePresenter,
            final KuneErrorHandler errorHandler) {
        tools = new HashMap<String, ToolSelectorItem>();
        stateManager.onStateChanged(new Listener<StateDTO>() {
            public void onEvent(StateDTO state) {
                for (String tool : tools.keySet()) {
                    List<String> enabledTools = state.getEnabledTools();
                    if (enabledTools != null && enabledTools.contains(tool)) {
                        tools.get(tool).setVisible(true);
                    } else {
                        tools.get(tool).setVisible(false);
                    }
                }
            }
        });
        stateManager.onGroupChanged(new Listener2<GroupDTO, GroupDTO>() {
            public void onEvent(final GroupDTO oldGroup, final GroupDTO newGroup) {
                onGroupChanged(newGroup.getShortName());
            }
        });
        stateManager.onToolChanged(new Listener2<String, String>() {
            public void onEvent(final String oldTool, final String newTool) {
                onToolChanged(oldTool, newTool);
            }
        });
        errorHandler.onNotDefaultContent(new Listener0() {
            public void onEvent() {
                setToolsVisible(false);
            }
        });

    }

    public void addTool(final ToolSelectorItem item) {
        final String name = item.getShortName();
        if (name == null) {
            throw new RuntimeException("You cannot add a tool without a name");
        }
        if (tools.get(name) != null) {
            throw new RuntimeException("A tool with the same name already added");
        }
        tools.put(name, item);
        item.setSelected(false);
    }

    void onGroupChanged(final String newGroupName) {
        for (final String name : tools.keySet()) {
            tools.get(name).setGroupShortName(newGroupName);
        }
    }

    void onToolChanged(final String oldTool, final String newTool) {
        if (oldTool != null) {
            tools.get(oldTool).setSelected(false);
        }
        tools.get(newTool).setSelected(true);
    }

    private void setToolsVisible(boolean visible) {
        for (String tool : tools.keySet()) {
            tools.get(tool).setVisible(visible);
        }
    }

}
