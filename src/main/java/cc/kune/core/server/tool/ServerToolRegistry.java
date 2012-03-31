/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.tool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.properties.KuneProperties;
import cc.kune.trash.shared.TrashToolConstants;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ServerToolRegistry {
  public static final List<String> emptyToolList = new ArrayList<String>();

  static Log log = LogFactory.getLog(ServerToolRegistry.class);

  private final List<String> groupsAvailableToolsInProp;
  private final List<String> groupsRegisEnabledTools;
  private final List<String> groupsRegisEnabledToolsInProp;
  private final HashMap<String, ServerTool> tools;
  private final List<ToolSimple> toolsAvailableForGroups;
  private final List<ToolSimple> toolsAvailableForUsers;
  private final List<String> usersAvailableToolsInProp;
  private final List<String> usersRegisEnabledTools;
  private final List<String> usersRegisEnabledToolsInProp;

  @Inject
  public ServerToolRegistry(final KuneProperties kuneProperties) {
    tools = new HashMap<String, ServerTool>();
    toolsAvailableForUsers = new ArrayList<ToolSimple>();
    toolsAvailableForGroups = new ArrayList<ToolSimple>();
    usersRegisEnabledTools = new ArrayList<String>();
    groupsRegisEnabledTools = new ArrayList<String>();
    usersAvailableToolsInProp = kuneProperties.getList(KuneProperties.SITE_USER_AVAILABLE_TOOLS);
    groupsAvailableToolsInProp = kuneProperties.getList(KuneProperties.SITE_GROUP_AVAILABLE_TOOLS);
    usersRegisEnabledToolsInProp = kuneProperties.getList(KuneProperties.SITE_USER_REGIST_ENABLED_TOOLS);
    groupsRegisEnabledToolsInProp = kuneProperties.getList(KuneProperties.SITE_GROUP_REGIST_ENABLED_TOOLS);
  }

  public Collection<ServerTool> all() {
    return tools.values();
  }

  public ServerTool get(final String toolName) {
    return tools.get(toolName);
  }

  public List<ToolSimple> getToolsAvailableForGroups() {
    return toolsAvailableForGroups;
  }

  public List<ToolSimple> getToolsAvailableForUsers() {
    return toolsAvailableForUsers;
  }

  public List<String> getToolsRegisEnabledForGroups() {
    return groupsRegisEnabledTools;
  }

  public List<String> getToolsRegisEnabledForUsers() {
    return usersRegisEnabledTools;
  }

  public void register(final ServerTool tool) {
    final String name = tool.getName();
    final String rootName = tool.getRootName();
    if (tool.getName().equals(TrashToolConstants.TOOL_NAME)) {
      tools.put(name, tool);
      // Trash is a special tool but we don't register it fully
      return;
    }
    final boolean userAvailable = usersAvailableToolsInProp.contains(name);
    final boolean groupAvailable = groupsAvailableToolsInProp.contains(name);
    if (userAvailable && usersRegisEnabledToolsInProp.contains(name)) {
      usersRegisEnabledTools.add(name);
    }
    if (groupAvailable && groupsRegisEnabledToolsInProp.contains(name)) {
      groupsRegisEnabledTools.add(name);
    }
    if (userAvailable || groupAvailable) {
      tools.put(name, tool);
      switch (tool.getTarget()) {
      case forGroups:
        if (groupAvailable) {
          toolsAvailableForGroups.add(new ToolSimple(name, rootName));
        }
        break;
      case forUsers:
        if (userAvailable) {
          toolsAvailableForUsers.add(new ToolSimple(name, rootName));
        }
        break;
      case forBoth:
        if (groupAvailable) {
          toolsAvailableForGroups.add(new ToolSimple(name, rootName));
        }
        if (userAvailable) {
          toolsAvailableForUsers.add(new ToolSimple(name, rootName));
        }
        break;
      }
    }
  }
}
