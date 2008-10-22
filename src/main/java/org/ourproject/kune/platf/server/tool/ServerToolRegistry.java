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

package org.ourproject.kune.platf.server.tool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Singleton;

@Singleton
public class ServerToolRegistry {
    static Log log = LogFactory.getLog(ServerToolRegistry.class);

    public static Collection<String> emptyToolList = new ArrayList<String>();

    private final HashMap<String, ServerTool> tools;
    // private final List<ToolSimple> toolsForGroupsList;
    // private final List<ToolSimple> toolsForUsersList;
    private final HashMap<String, ToolSimple> toolsForUsersMap;
    private final HashMap<String, ToolSimple> toolsForGroupsMap;

    public ServerToolRegistry() {
        tools = new HashMap<String, ServerTool>();
        toolsForUsersMap = new HashMap<String, ToolSimple>();
        toolsForGroupsMap = new HashMap<String, ToolSimple>();
        // toolsForGroupsList = new ArrayList<ToolSimple>();
        // toolsForUsersList = new ArrayList<ToolSimple>();
    }

    public Collection<ServerTool> all() {
        return tools.values();
    }

    public ServerTool get(final String toolName) {
        return tools.get(toolName);
    }

    //
    // public List<ToolSimple> getToolsForGroupsList() {
    // return toolsForGroupsList;
    // }

    public Collection<ToolSimple> getToolsForGroups() {
        return toolsForGroupsMap.values();
    }

    public Collection<String> getToolsForGroupsKeys() {
        return toolsForGroupsMap.keySet();
    }

    public Collection<String> getToolsForUserKeys() {
        return toolsForUsersMap.keySet();
    }

    public Collection<ToolSimple> getToolsForUsers() {
        return toolsForUsersMap.values();
    }

    // public List<ToolSimple> getToolsForUsersList() {
    // return toolsForUsersList;
    // }

    public void register(final ServerTool tool) {
        String name = tool.getName();
        String rootName = tool.getRootName();
        tools.put(name, tool);
        switch (tool.getTarget()) {
        case forGroups:
            toolsForGroupsMap.put(name, new ToolSimple(name, rootName));
            break;
        case forUsers:
            // toolsForUsersList.add(new ToolSimple(name, rootName));
            toolsForUsersMap.put(name, new ToolSimple(name, rootName));
            break;
        case forBoth:
            toolsForGroupsMap.put(name, new ToolSimple(name, rootName));
            toolsForUsersMap.put(name, new ToolSimple(name, rootName));
            // toolsForUsersList.add(new ToolSimple(name, rootName));
            break;
        }
    }
}
