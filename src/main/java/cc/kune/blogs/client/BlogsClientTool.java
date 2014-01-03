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
package cc.kune.blogs.client;

import static cc.kune.blogs.shared.BlogsToolConstants.*;
import cc.kune.blogs.shared.BlogsToolConstants;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class BlogsClientTool.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BlogsClientTool extends FoldableAbstractClientTool {

  /**
   * Instantiates a new blogs client tool.
   *
   * @param i18n the i18n
   * @param toolSelector the tool selector
   * @param cntCapRegistry the cnt cap registry
   * @param history the history
   */
  @Inject
  public BlogsClientTool(final I18nTranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final HistoryWrapper history) {
    super(
        TOOL_NAME,
        i18n.t(BlogsToolConstants.ROOT_NAME),
        i18n.t("Blogs are a chronological list of posts (ordered by date) about a specific topic. Each post can be commented by the visitors"),
        ICON_TYPE_ROOT, AccessRolDTO.Viewer, toolSelector, cntCapRegistry, i18n, history);

    // registerAclEditableTypes();
    registerAuthorableTypes(TYPE_POST, TYPE_UPLOADEDFILE);
    registerDragableTypes(TYPE_UPLOADEDFILE);
    registerDropableTypes(TYPE_ROOT, TYPE_POST);
    registerPublishModerableTypes(TYPE_POST, TYPE_UPLOADEDFILE);
    registerRateableTypes(TYPE_POST, TYPE_UPLOADEDFILE);
    registerRenamableTypes(TYPE_BLOG, TYPE_POST, TYPE_UPLOADEDFILE);
    registerTageableTypes(TYPE_BLOG, TYPE_UPLOADEDFILE, TYPE_POST);
    // registerTranslatableTypes();
    registerIcons();
  }

  /* (non-Javadoc)
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
    // registerTutorial(TYPE_BLOG);
    registerContentTypeIcon(TYPE_ROOT, ICON_TYPE_ROOT);
    registerContentTypeIcon(TYPE_BLOG, ICON_TYPE_BLOG);
    registerContentTypeIcon(TYPE_POST, ICON_TYPE_POST);
    registerEmptyMessages(TYPE_ROOT, i18n.t("There isn't any blog, you can create one"));
    registerEmptyMessages(TYPE_BLOG, i18n.t("This blog doesn't have any posts, you can create one"));
    registerEmptyMessagesNotLogged(TYPE_ROOT, i18n.t("There isn't any blog"));
    registerEmptyMessagesNotLogged(TYPE_BLOG, i18n.t("This blog doesn't have any posts"));
  }

}
