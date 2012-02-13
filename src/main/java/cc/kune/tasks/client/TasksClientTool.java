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
package cc.kune.tasks.client;

import static cc.kune.tasks.shared.TasksConstants.NAME;
import static cc.kune.tasks.shared.TasksConstants.ROOT_NAME;
import static cc.kune.tasks.shared.TasksConstants.TYPE_FOLDER;
import static cc.kune.tasks.shared.TasksConstants.TYPE_ROOT;
import static cc.kune.tasks.shared.TasksConstants.TYPE_TASK;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

public class TasksClientTool extends FoldableAbstractClientTool {

  private static final String NO_TASK = "There isn't any task";

  @Inject
  public TasksClientTool(final I18nTranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final NavResources navResources,
      final IconicResources icons) {
    super(
        NAME,
        i18n.t(ROOT_NAME),
        i18n.t("A collaborative TO-DO list for the group. Any group-member can participate in any proposed task, add others to a task, comment them, add info, etc"),
        icons.tasks(), toolSelector, cntCapRegistry, i18n, navResources);

    // registerAclEditableTypes();
    registerAuthorableTypes(TYPE_TASK);
    registerDragableTypes(TYPE_TASK, TYPE_FOLDER);
    registerDropableTypes(TYPE_ROOT, TYPE_FOLDER);
    registerPublishModerableTypes(TYPE_TASK);
    registerRateableTypes(TYPE_TASK);
    registerRenamableTypes(TYPE_FOLDER, TYPE_TASK);
    registerTageableTypes(TYPE_FOLDER, TYPE_TASK);
    // registerTranslatableTypes();
    registerIcons();
  }

  @Override
  public String getName() {
    return NAME;
  }

  private void registerIcons() {
    registerContentTypeIcon(TYPE_ROOT, navResources.taskfolder());
    registerContentTypeIcon(TYPE_FOLDER, navResources.taskfolder());
    registerContentTypeIcon(TYPE_TASK, navResources.task());
    registerContentTypeIcon(TYPE_TASK, ContentStatus.inTheDustbin, navResources.taskdone());
    final String noTaskLogged = i18n.t(NO_TASK + ", create one");
    registerEmptyMessages(TYPE_ROOT, noTaskLogged);
    registerEmptyMessages(TYPE_FOLDER, noTaskLogged);
    registerEmptyMessagesNotLogged(TYPE_ROOT, NO_TASK);
    registerEmptyMessagesNotLogged(TYPE_FOLDER, NO_TASK);
    registerShowDeleted(TYPE_FOLDER, TYPE_ROOT, TYPE_TASK);
  }

}
