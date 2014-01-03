/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

import static cc.kune.lists.shared.ListsToolConstants.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.server.AbstractWaveBasedServerTool;
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

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class ListsServerTool.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ListsServerTool extends AbstractWaveBasedServerTool {

  /** The user session manager. */
  private final UserSessionManager userSessionManager;

  /**
   * Instantiates a new lists server tool.
   * 
   * @param contentManager
   *          the content manager
   * @param containerManager
   *          the container manager
   * @param configurationManager
   *          the configuration manager
   * @param i18n
   *          the i18n
   * @param userSessionManager
   *          the user session manager
   * @param creationService
   *          the creation service
   */
  @Inject
  public ListsServerTool(final ContentManager contentManager, final ContainerManager containerManager,
      final ToolConfigurationManager configurationManager, final I18nTranslationService i18n,
      final UserSessionManager userSessionManager, final CreationService creationService) {
    super(TOOL_NAME, ROOT_NAME, TYPE_ROOT, Arrays.asList(TYPE_POST), Arrays.asList(TYPE_LIST),
        Arrays.asList(TYPE_LIST), Arrays.asList(TYPE_ROOT), contentManager, containerManager,
        creationService, configurationManager, i18n, ServerToolTarget.forGroups);
    this.userSessionManager = userSessionManager;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.AbstractWaveBasedServerTool#getNewContentAdditionalParts
   * (cc.kune.domain.Container)
   */
  @Override
  public String[] getNewContentAdditionalParts(final Container parent) {
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
    return members.toArray(new String[members.size()]);
  }

  /**
   * Gets the user group.
   * 
   * @return the user group
   */
  private Group getUserGroup() {
    return userSessionManager.getUser().getUserGroup();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.tool.ServerTool#initGroup(cc.kune.domain.User,
   * cc.kune.domain.Group, java.lang.Object[])
   */
  @Override
  public Group initGroup(final User user, final Group group, final Object... otherVars) {
    createRoot(group);
    return group;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.AbstractServerTool#onCreateContent(cc.kune.domain.Content
   * , cc.kune.domain.Container)
   */
  @Override
  public void onCreateContent(final Content content, final Container parent) {
    // TODO Fix closed list??
    content.setStatus(ContentStatus.publishedOnline);
    content.setPublishedOn(new Date());
  }

}
