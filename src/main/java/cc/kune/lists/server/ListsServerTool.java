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
 */
package cc.kune.lists.server;

import static cc.kune.lists.shared.ListsConstants.NAME;
import static cc.kune.lists.shared.ListsConstants.ROOT_NAME;
import static cc.kune.lists.shared.ListsConstants.TYPE_LIST;
import static cc.kune.lists.shared.ListsConstants.TYPE_ROOT;
import static cc.kune.lists.shared.ListsConstants.TYPE_POST;

import java.util.Arrays;

import cc.kune.core.server.AbstractServerTool;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;

public class ListsServerTool extends AbstractServerTool {

  @Inject
  public ListsServerTool(final ContentManager contentManager, final ContainerManager containerManager,
      final ToolConfigurationManager configurationManager, final I18nTranslationService i18n) {
    super(NAME, ROOT_NAME, TYPE_ROOT, Arrays.asList(TYPE_POST), Arrays.asList(TYPE_LIST),
        Arrays.asList(TYPE_LIST), Arrays.asList(TYPE_ROOT), contentManager, containerManager,
        configurationManager, i18n, ServerToolTarget.forGroups);
  }

  @Override
  public Group initGroup(final User user, final Group group, final Object... otherVars) {
    createRoot(group);
    return group;
  }
}
