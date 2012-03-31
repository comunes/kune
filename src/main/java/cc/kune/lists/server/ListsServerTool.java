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

import static cc.kune.lists.shared.ListsToolConstants.TOOL_NAME;
import static cc.kune.lists.shared.ListsToolConstants.ROOT_NAME;
import static cc.kune.lists.shared.ListsToolConstants.TYPE_LIST;
import static cc.kune.lists.shared.ListsToolConstants.TYPE_POST;
import static cc.kune.lists.shared.ListsToolConstants.TYPE_ROOT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.server.AbstractServerTool;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.User;
import cc.kune.wave.server.KuneWaveServerUtils;
import cc.kune.wave.server.kspecific.KuneWaveService;

import com.google.inject.Inject;

public class ListsServerTool extends AbstractServerTool {

  private final UserSessionManager userSessionManager;
  private final KuneWaveService waveManager;

  @Inject
  public ListsServerTool(final ContentManager contentManager, final ContainerManager containerManager,
      final ToolConfigurationManager configurationManager, final I18nTranslationService i18n,
      final UserSessionManager userSessionManager, final KuneWaveService waveManager,
      final CreationService creationService) {
    super(TOOL_NAME, ROOT_NAME, TYPE_ROOT, Arrays.asList(TYPE_POST), Arrays.asList(TYPE_LIST),
        Arrays.asList(TYPE_LIST), Arrays.asList(TYPE_ROOT), contentManager, containerManager,
        creationService, configurationManager, i18n, ServerToolTarget.forGroups);
    this.userSessionManager = userSessionManager;
    this.waveManager = waveManager;
  }

  private Group getUserGroup() {
    return userSessionManager.getUser().getUserGroup();
  }

  @Override
  public Group initGroup(final User user, final Group group, final Object... otherVars) {
    createRoot(group);
    return group;
  }

  @Override
  public void onCreateContent(final Content content, final Container parent) {
    content.setStatus(ContentStatus.publishedOnline);
    content.setPublishedOn(new Date());
    final Set<Group> admins = parent.getAccessLists().getAdmins().getList();
    final Set<Group> editors = parent.getAccessLists().getEditors().getList();
    final ArrayList<String> members = new ArrayList<String>();
    for (final Group admin : admins) {
      members.add(admin.getShortName());
    }
    for (final Group editor : editors) {
      members.add(editor.getShortName());
    }
    members.add(getUserGroup().getShortName());
    Collections.sort(members);
    waveManager.addParticipants(KuneWaveServerUtils.getWaveRef(content),
        content.getAuthors().get(0).getShortName(), getUserGroup().getShortName(),
        members.toArray(new String[members.size()]));
  }

}
