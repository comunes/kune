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
package cc.kune.tasks.server;

import static cc.kune.tasks.shared.TasksConstants.NAME;
import static cc.kune.tasks.shared.TasksConstants.ROOT_NAME;
import static cc.kune.tasks.shared.TasksConstants.TYPE_FOLDER;
import static cc.kune.tasks.shared.TasksConstants.TYPE_ROOT;
import static cc.kune.tasks.shared.TasksConstants.TYPE_TASK;

import java.util.Arrays;
import java.util.Date;

import cc.kune.core.server.AbstractServerTool;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.User;

import com.google.inject.Inject;

public class TaskServerTool extends AbstractServerTool {

  @Inject
  public TaskServerTool(final ContentManager contentManager, final ContainerManager containerManager,
      final ToolConfigurationManager configurationManager, final I18nTranslationService i18n) {
    super(NAME, ROOT_NAME, TYPE_ROOT, Arrays.asList(TYPE_TASK), Arrays.asList(TYPE_FOLDER, TYPE_ROOT),
        Arrays.asList(TYPE_FOLDER), Arrays.asList(TYPE_ROOT, TYPE_FOLDER), contentManager,
        containerManager, configurationManager, i18n, ServerToolTarget.forBoth);
  }

  private Container createFolder(final Group group, final Container rootFolder,
      final I18nLanguage language, final String title) {
    final Container shortTerm = containerManager.createFolder(group, rootFolder, i18n.t(title),
        language, TYPE_FOLDER);
    return shortTerm;
  }

  private void createTask(final User user, final Group group, final Container shortTerm,
      final String text) {
    final Content content = createInitialContent(user, group, shortTerm, i18n.t(text),
        i18n.t("This is only a task sample. You can edit it, rename it"), TYPE_TASK);
    contentManager.save(content);
  }

  @Override
  public Group initGroup(final User user, final Group group, final Object... otherVars) {
    final Container rootFolder = createRoot(group);

    final I18nLanguage language = user.getLanguage();

    final Container longTerm = createFolder(group, rootFolder, language, "Long-term tasks");
    final Container midTerm = createFolder(group, rootFolder, language, "Mid-term tasks");
    final Container shortTerm = createFolder(group, rootFolder, language, "Short-term (urgent) tasks");

    createTask(user, group, longTerm, "A long-term task sample");
    createTask(user, group, midTerm, "A mid-term task sample");
    createTask(user, group, shortTerm, "A short-term task sample");
    return group;
  }

  @Override
  public void onCreateContent(final Content content, final Container parent) {
    content.setStatus(ContentStatus.publishedOnline);
    content.setPublishedOn(new Date());
  }
}
