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
package cc.kune.tasks.client;

import static cc.kune.tasks.shared.TasksToolConstants.*;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class TasksClientTool.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TasksClientTool extends FoldableAbstractClientTool {

  /** The Constant NO_TASK. */
  private static final String NO_TASK = "There isn't any task";

  /**
   * Instantiates a new tasks client tool.
   * 
   * @param i18n
   *          the i18n
   * @param toolSelector
   *          the tool selector
   * @param cntCapRegistry
   *          the cnt cap registry
   * @param history
   *          the history
   */
  @Inject
  public TasksClientTool(final I18nTranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final HistoryWrapper history) {
    super(
        TOOL_NAME,
        i18n.t(ROOT_NAME),
        i18n.t("A collaborative TO-DO list for the group. Any group-member can participate in any proposed task, add others to a task, comment them, add info, etc"),
        ICON_TYPE_ROOT, AccessRolDTO.Viewer, toolSelector, cntCapRegistry, i18n, history);

    // registerAclEditableTypes();
    registerAuthorableTypes(TYPE_TASK);
    registerDragableTypes(TYPE_TASK, TYPE_FOLDER);
    registerDropableTypes(TYPE_ROOT, TYPE_FOLDER, TYPE_TASK);
    registerPublishModerableTypes(TYPE_TASK);
    registerRateableTypes(TYPE_TASK);
    registerRenamableTypes(TYPE_FOLDER, TYPE_TASK);
    registerTageableTypes(TYPE_FOLDER, TYPE_TASK);
    // registerTranslatableTypes();
    registerIcons();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.tool.AbstractClientTool#getName()
   */
  @Override
  public String getName() {
    return TOOL_NAME;
  }

  /**
   * Register icons.
   */
  private void registerIcons() {
    registerEmptyFolderTutorial(TYPE_ROOT);
    // registerTutorial(TYPE_FOLDER);
    registerContentTypeIcon(TYPE_ROOT, ICON_TYPE_TASK);
    registerContentTypeIcon(TYPE_FOLDER, ICON_TYPE_FOLDER);
    registerContentTypeIcon(TYPE_TASK, ICON_TYPE_TASK);
    // FIXME: Do a different icon to completed tasks
    registerContentTypeIcon(TYPE_TASK, ContentStatus.inTheDustbin, ICON_TYPE_TASK);
    final String noTaskLogged = i18n.t(NO_TASK + ", create one");
    final String noTaskNotLogged = i18n.t(NO_TASK);
    registerEmptyMessages(TYPE_ROOT, noTaskLogged);
    registerEmptyMessages(TYPE_FOLDER, noTaskLogged);
    registerEmptyMessagesNotLogged(TYPE_ROOT, noTaskNotLogged);
    registerEmptyMessagesNotLogged(TYPE_FOLDER, noTaskNotLogged);
    registerShowDeleted(TYPE_FOLDER, TYPE_ROOT, TYPE_TASK);
  }

}
