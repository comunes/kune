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
package cc.kune.barters.client;

import static cc.kune.barters.shared.BartersToolConstants.*;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class BartersClientTool.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BartersClientTool extends FoldableAbstractClientTool {

  /** The Constant THERE_ISN_T_ANY_BARTER. */
  private static final String THERE_ISN_T_ANY_BARTER = "There isn't any barter. ";

  /**
   * Instantiates a new barters client tool.
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
  public BartersClientTool(final I18nTranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final HistoryWrapper history) {
    super(
        TOOL_NAME,
        i18n.t("barters"),
        i18n.t("A decentralized way to offer (or ask for) services and goods to your groups or to anyone. Bartering means the exchange of goods by the agreement of two people"),
        ICON_TYPE_ROOT, AccessRolDTO.Viewer, toolSelector, cntCapRegistry, i18n, history);

    // registerAclEditableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
    registerAuthorableTypes(TYPE_BARTER);
    registerDragableTypes(TYPE_BARTER, TYPE_FOLDER);
    registerDropableTypes(TYPE_ROOT, TYPE_FOLDER, TYPE_BARTER);
    // registerPublishModerableTypes();
    registerRateableTypes(TYPE_BARTER);
    registerRenamableTypes(TYPE_FOLDER, TYPE_BARTER);
    registerTageableTypes(TYPE_BARTER);
    registerTranslatableTypes(TYPE_FOLDER, TYPE_BARTER);
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
    registerContentTypeIcon(TYPE_ROOT, ICON_TYPE_ROOT);
    registerContentTypeIcon(TYPE_FOLDER, ICON_TYPE_FOLDER);
    registerContentTypeIcon(TYPE_BARTER, ICON_TYPE_BARTER);
    registerEmptyMessages(TYPE_FOLDER,
        i18n.t(THERE_ISN_T_ANY_BARTER + "You can create some of them here"));
    registerEmptyMessages(TYPE_ROOT, i18n.t(THERE_ISN_T_ANY_BARTER));
    registerEmptyMessagesNotLogged(TYPE_FOLDER, i18n.t(THERE_ISN_T_ANY_BARTER));
    registerEmptyMessagesNotLogged(TYPE_ROOT, i18n.t(THERE_ISN_T_ANY_BARTER));
  }

}
