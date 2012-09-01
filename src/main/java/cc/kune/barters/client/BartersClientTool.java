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
package cc.kune.barters.client;

import static cc.kune.barters.shared.BartersToolConstants.TOOL_NAME;
import static cc.kune.barters.shared.BartersToolConstants.TYPE_BARTER;
import static cc.kune.barters.shared.BartersToolConstants.TYPE_FOLDER;
import static cc.kune.barters.shared.BartersToolConstants.TYPE_ROOT;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

public class BartersClientTool extends FoldableAbstractClientTool {

  private static final String THERE_ISN_T_ANY_BARTER = "There isn't any barter. ";
  private final IconicResources icons;

  @Inject
  public BartersClientTool(final I18nTranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final NavResources navResources,
      final IconicResources icons, HistoryWrapper history) {
    super(
        TOOL_NAME,
        i18n.t("barters"),
        i18n.t("A decentralized way to offer (or ask for) services and goods to your groups or to anyone. Bartering means the exchange of goods by the agreement of two people"),
        icons.barters(), AccessRolDTO.Viewer, toolSelector, cntCapRegistry, i18n, navResources, history);
    this.icons = icons;

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

  @Override
  public String getName() {
    return TOOL_NAME;
  }

  private void registerIcons() {
    registerEmptyFolderTutorial(TYPE_ROOT);
    // registerTutorial(TYPE_FOLDER);
    registerContentTypeIcon(TYPE_FOLDER, navResources.folder());
    registerContentTypeIcon(TYPE_ROOT, icons.barters());
    registerContentTypeIcon(TYPE_BARTER, navResources.barter());
    registerEmptyMessages(TYPE_FOLDER, i18n.t(THERE_ISN_T_ANY_BARTER + "You can create some of them here"));
    registerEmptyMessages(TYPE_ROOT, i18n.t(THERE_ISN_T_ANY_BARTER));
    registerEmptyMessagesNotLogged(TYPE_FOLDER, i18n.t(THERE_ISN_T_ANY_BARTER));
    registerEmptyMessagesNotLogged(TYPE_ROOT, i18n.t(THERE_ISN_T_ANY_BARTER));
  }

}
