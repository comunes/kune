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
package cc.kune.wiki.client;

import static cc.kune.wiki.shared.WikiToolConstants.*;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

public class WikiClientTool extends FoldableAbstractClientTool {

  private static final String EMPTY_FOLDER = "This folder is empty, you can create a wikipage or folder here";
  private final IconicResources icons;

  @Inject
  public WikiClientTool(final I18nUITranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final NavResources navResources,
      final IconicResources icons, final HistoryWrapper history) {
    super(
        TOOL_NAME,
        i18n.t("wiki"),
        i18n.t(
            "Wiki-pages are Documents that can be edited by any visitor (in [%s]), instead of only by the group. This accelerates updating and construction",
            i18n.getSiteCommonName()), icons.wikisWhite(), AccessRolDTO.Viewer, toolSelector,
        cntCapRegistry, i18n, navResources, history);
    this.icons = icons;

    registerAuthorableTypes(TYPE_WIKIPAGE, TYPE_UPLOADEDFILE);
    registerDragableTypes(TYPE_WIKIPAGE, TYPE_FOLDER, TYPE_UPLOADEDFILE);
    registerDropableTypes(TYPE_ROOT, TYPE_FOLDER, TYPE_WIKIPAGE);
    registerRateableTypes(TYPE_UPLOADEDFILE, TYPE_WIKIPAGE);
    registerRenamableTypes(TYPE_FOLDER, TYPE_UPLOADEDFILE, TYPE_WIKIPAGE);
    registerTageableTypes(TYPE_UPLOADEDFILE, TYPE_WIKIPAGE);
    registerTranslatableTypes(TYPE_FOLDER, TYPE_WIKIPAGE);

    registerIcons();
  }

  @Override
  public String getName() {
    return TOOL_NAME;
  }

  private void registerIcons() {
    registerEmptyFolderTutorial(TYPE_ROOT);
    // registerTutorial(TYPE_FOLDER);
    registerContentTypeIcon(TYPE_ROOT, icons.wikisGrey());
    registerContentTypeIcon(TYPE_FOLDER, icons.wikisFolderGrey());
    registerContentTypeIcon(TYPE_WIKIPAGE, icons.wikisGrey());
    registerContentTypeIconLight(TYPE_ROOT, icons.wikisWhite());
    registerContentTypeIconLight(TYPE_FOLDER, icons.wikisFolderWhite());
    registerContentTypeIconLight(TYPE_WIKIPAGE, icons.wikisWhite());
    registerUploadTypesAndMimes(TYPE_UPLOADEDFILE);
    registerEmptyMessages(TYPE_FOLDER, i18n.t(EMPTY_FOLDER));
    registerEmptyMessages(TYPE_ROOT, i18n.t(EMPTY_FOLDER));
    registerEmptyMessagesNotLogged(TYPE_FOLDER, i18n.t("This folder is empty"));
    registerEmptyMessagesNotLogged(TYPE_ROOT, i18n.t("This wiki is empty"));
  }

}
