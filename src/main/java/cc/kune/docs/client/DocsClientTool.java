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
package cc.kune.docs.client;

import static cc.kune.docs.shared.DocsToolConstants.TOOL_NAME;
import static cc.kune.docs.shared.DocsToolConstants.TYPE_DOCUMENT;
import static cc.kune.docs.shared.DocsToolConstants.TYPE_FOLDER;
import static cc.kune.docs.shared.DocsToolConstants.TYPE_ROOT;
import static cc.kune.docs.shared.DocsToolConstants.TYPE_UPLOADEDFILE;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.docs.shared.DocsToolConstants;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

public class DocsClientTool extends FoldableAbstractClientTool {

  private static final String EMPTY_CREATE_SOME = "This folder is empty. You can create some document or folder here.";
  private final IconicResources icons;

  @Inject
  public DocsClientTool(final I18nTranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final NavResources navResources,
      final IconicResources icons) {
    super(
        TOOL_NAME,
        i18n.t(DocsToolConstants.ROOT_NAME),
        i18n.t("Here you can create or upload your personal, group or public documents. These documents can be edited and commented collaboratively and simultaneously. These docs can be static pages in your web page if you publish them"),
        icons.docs(), AccessRolDTO.Viewer, toolSelector, cntCapRegistry, i18n, navResources);
    this.icons = icons;

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

  @Override
  public String getName() {
    return TOOL_NAME;
  }

  private void registerIcons() {
    registerEmptyFolderTutorial(TYPE_ROOT);
    // registerTutorial(TYPE_FOLDER);
    registerContentTypeIcon(TYPE_FOLDER, navResources.folder());
    registerContentTypeIcon(TYPE_ROOT, icons.docs());
    registerContentTypeIcon(TYPE_DOCUMENT, navResources.page());
    registerUploadTypesAndMimes(TYPE_UPLOADEDFILE);
    registerEmptyMessagesNotLogged(TYPE_FOLDER, i18n.t(EMPTY));
    registerEmptyMessagesNotLogged(TYPE_ROOT, i18n.t(EMPTY));
    registerEmptyMessages(TYPE_FOLDER, i18n.t(EMPTY_CREATE_SOME));
    registerEmptyMessages(TYPE_ROOT, i18n.t(EMPTY_CREATE_SOME));
  }

}
