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
package cc.kune.trash.client;

import static cc.kune.trash.shared.TrashToolConstants.*;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class TrashClientTool.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TrashClientTool extends FoldableAbstractClientTool {

  /** The Constant EMPTY. */
  private static final String EMPTY = "The trash is empty";

  /**
   * Instantiates a new trash client tool.
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
  public TrashClientTool(final I18nTranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final HistoryWrapper history) {
    super(TOOL_NAME, i18n.t(ROOT_NAME), i18n.t("You can drop here contents to delete then"),
        ICON_TYPE_ROOT, AccessRolDTO.Editor, toolSelector, cntCapRegistry, i18n, history);

    // registerAclEditableTypes();
    registerAuthorableTypes();
    registerDragableTypes();
    registerDropableTypes(TYPE_ROOT);
    registerPublishModerableTypes();
    registerRateableTypes();
    registerRenamableTypes();
    registerTageableTypes();
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
    registerContentTypeIcon(TYPE_ROOT, ICON_TYPE_ROOT);
    final String trashEmpty = i18n.t(EMPTY);
    registerEmptyMessages(TYPE_ROOT, trashEmpty);
    registerEmptyMessagesNotLogged(TYPE_ROOT, trashEmpty);
    registerShowDeleted(TYPE_ROOT);
  }

}
