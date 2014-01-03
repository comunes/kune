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
package cc.kune.wiki.client;

import static cc.kune.wiki.shared.WikiToolConstants.*;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class WikiClientTool.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WikiClientTool extends FoldableAbstractClientTool {

  /** The Constant EMPTY_FOLDER. */
  private static final String EMPTY_FOLDER = "This folder is empty, you can create a wikipage or folder here";

  /**
   * Instantiates a new wiki client tool.
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
  public WikiClientTool(final I18nTranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final HistoryWrapper history) {
    super(
        TOOL_NAME,
        i18n.t("wiki"),
        i18n.t(
            "Wiki-pages are Documents that can be edited by any visitor (in [%s]), instead of only by the group. This accelerates updating and construction",
            i18n.getSiteCommonName()), ICON_TYPE_ROOT, AccessRolDTO.Viewer, toolSelector,
        cntCapRegistry, i18n, history);

    registerAuthorableTypes(TYPE_WIKIPAGE, TYPE_UPLOADEDFILE);
    registerDragableTypes(TYPE_WIKIPAGE, TYPE_FOLDER, TYPE_UPLOADEDFILE);
    registerDropableTypes(TYPE_ROOT, TYPE_FOLDER, TYPE_WIKIPAGE);
    registerRateableTypes(TYPE_UPLOADEDFILE, TYPE_WIKIPAGE);
    registerRenamableTypes(TYPE_FOLDER, TYPE_UPLOADEDFILE, TYPE_WIKIPAGE);
    registerTageableTypes(TYPE_UPLOADEDFILE, TYPE_WIKIPAGE);
    registerTranslatableTypes(TYPE_FOLDER, TYPE_WIKIPAGE);

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
    registerContentTypeIcon(TYPE_WIKIPAGE, ICON_TYPE_WIKIPAGE);
    registerEmptyMessages(TYPE_FOLDER, i18n.t(EMPTY_FOLDER));
    registerEmptyMessages(TYPE_ROOT, i18n.t(EMPTY_FOLDER));
    registerEmptyMessagesNotLogged(TYPE_FOLDER, i18n.t("This folder is empty"));
    registerEmptyMessagesNotLogged(TYPE_ROOT, i18n.t("This wiki is empty"));
  }

}
