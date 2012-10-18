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
package cc.kune.trash.client;

import static cc.kune.trash.shared.TrashToolConstants.*;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

public class TrashClientTool extends FoldableAbstractClientTool {

  private static final String EMPTY = "The trash is empty";
  private final IconicResources res;

  @Inject
  public TrashClientTool(final I18nTranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final IconicResources res,
      final HistoryWrapper history) {
    super(TOOL_NAME, i18n.t(ROOT_NAME), i18n.t("You can drop here contents to delete then"),
        res.trashWhite(), AccessRolDTO.Editor, toolSelector, cntCapRegistry, i18n, history);
    this.res = res;

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

  @Override
  public String getName() {
    return TOOL_NAME;
  }

  private void registerIcons() {
    registerContentTypeIcon(TYPE_ROOT, res.trashGrey());
    registerContentTypeIconLight(TYPE_ROOT, res.trashWhite());
    final String trashEmpty = i18n.t(EMPTY);
    registerEmptyMessages(TYPE_ROOT, trashEmpty);
    registerEmptyMessagesNotLogged(TYPE_ROOT, trashEmpty);
    registerShowDeleted(TYPE_ROOT);
  }

}
