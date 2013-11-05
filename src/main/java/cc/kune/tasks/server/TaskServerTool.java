/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.tasks.server;

import static cc.kune.tasks.shared.TasksToolConstants.*;

import java.util.Arrays;
import java.util.Date;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.server.AbstractWaveBasedServerTool;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.User;

import com.google.inject.Inject;

public class TaskServerTool extends AbstractWaveBasedServerTool {

  @Inject
  public TaskServerTool(final ContentManager contentManager, final ContainerManager containerManager,
      final ToolConfigurationManager configurationManager, final I18nTranslationService i18n,
      final CreationService creationService) {
    super(TOOL_NAME, ROOT_NAME, TYPE_ROOT, Arrays.asList(TYPE_TASK), Arrays.asList(TYPE_FOLDER,
        TYPE_ROOT), Arrays.asList(TYPE_FOLDER), Arrays.asList(TYPE_ROOT, TYPE_FOLDER), contentManager,
        containerManager, creationService, configurationManager, i18n, ServerToolTarget.forBoth);
  }

  @SuppressWarnings("unused")
  private Container createFolder(final Group group, final Container rootFolder,
      final I18nLanguage language, final String title) {
    final Container shortTerm = creationService.createFolder(group, rootFolder.getId(), i18n.t(title),
        language, TYPE_FOLDER);
    return shortTerm;
  }

  @SuppressWarnings("unused")
  private void createTask(final User user, final Group group, final Container shortTerm,
      final String text) {
    createInitialContent(user, group, shortTerm, i18n.t(text),
        i18n.t("This is only a task sample. You can edit it, rename it"), TYPE_TASK);
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
  }
}
