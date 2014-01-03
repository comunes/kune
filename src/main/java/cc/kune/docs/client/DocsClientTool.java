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
package cc.kune.docs.client;

import static cc.kune.docs.shared.DocsToolConstants.*;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.docs.shared.DocsToolConstants;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class DocsClientTool.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class DocsClientTool extends FoldableAbstractClientTool {

  /** The Constant EMPTY_CREATE_SOME. */
  private static final String EMPTY_CREATE_SOME = "This folder is empty. You can create a document or folder here.";

  /**
   * Instantiates a new docs client tool.
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
  public DocsClientTool(final I18nTranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final HistoryWrapper history) {
    super(
        TOOL_NAME,
        i18n.t(DocsToolConstants.ROOT_NAME),
        i18n.t("Here you can create or upload your personal, group or public documents. These documents can be edited and commented collaboratively and simultaneously. These docs could be static pages in your public web page in case you publish them"),
        ICON_TYPE_ROOT, AccessRolDTO.Viewer, toolSelector, cntCapRegistry, i18n, history);

    // registerAclEditableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
    registerAuthorableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
    registerDragableTypes(TYPE_DOCUMENT, TYPE_FOLDER, TYPE_UPLOADEDFILE);
    registerDropableTypes(TYPE_ROOT, TYPE_FOLDER, TYPE_DOCUMENT);
    registerPublishModerableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
    registerRateableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
    registerRenamableTypes(TYPE_DOCUMENT, TYPE_FOLDER, TYPE_UPLOADEDFILE);
    registerTageableTypes(TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
    registerTranslatableTypes(TYPE_DOCUMENT, TYPE_FOLDER);
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
    registerContentTypeIcon(TYPE_DOCUMENT, ICON_TYPE_DOCUMENT);
    registerEmptyMessagesNotLogged(TYPE_FOLDER, i18n.t(EMPTY));
    registerEmptyMessagesNotLogged(TYPE_ROOT, i18n.t(EMPTY));
    registerEmptyMessages(TYPE_FOLDER, i18n.t(EMPTY_CREATE_SOME));
    registerEmptyMessages(TYPE_ROOT, i18n.t(EMPTY_CREATE_SOME));
  }

}
