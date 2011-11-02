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
package cc.kune.chat.client;

import static cc.kune.chat.shared.ChatConstants.NAME;
import static cc.kune.chat.shared.ChatConstants.TYPE_ROOM;
import cc.kune.chat.client.resources.ChatResources;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

public class ChatClientTool extends FoldableAbstractClientTool {

  private final ChatResources res;

  @Inject
  public ChatClientTool(final I18nTranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final NavResources navResources,
      final ChatResources res) {
    // FIXME: change this in ChatConstants and in db via migration
    super(NAME, i18n.t("chatrooms"), i18n.t(""), toolSelector, cntCapRegistry, i18n, navResources);
    this.res = res;
    registerIcons();
  }

  @Override
  public String getName() {
    return NAME;
  }

  private void registerIcons() {
    registerContentTypeIcon(TYPE_ROOM, res.groupChat());
    registerEmptyMessages(TYPE_ROOM, i18n.tWithNT("See the archive of old conversations"
        + TextUtils.IN_DEVELOPMENT_P, "with Brackets"));
  }

}
