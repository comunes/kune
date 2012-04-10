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

import static cc.kune.chat.shared.ChatToolConstants.TOOL_NAME;
import static cc.kune.chat.shared.ChatToolConstants.TYPE_ROOM;
import static cc.kune.chat.shared.ChatToolConstants.TYPE_ROOT;
import cc.kune.chat.client.resources.ChatResources;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

public class ChatClientTool extends FoldableAbstractClientTool {

  private final IconicResources icons;
  private final ChatResources res;

  @Inject
  public ChatClientTool(final I18nUITranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final NavResources navResources,
      final ChatResources res, final IconicResources icons) {
    // FIXME: change this in ChatConstants and in db via migration
    super(
        TOOL_NAME,
        i18n.t("chatrooms"),
        i18n.t(
            "A 'room' where you can have a group-chat with many users at once. Rooms can be public or private. Users can be from [%s] or other sites (compatible with gmail chat)",
            i18n.getSiteCommonName()), icons.chats(), AccessRolDTO.Viewer, toolSelector, cntCapRegistry,
        i18n, navResources);
    this.res = res;
    this.icons = icons;
    registerIcons();
  }

  @Override
  public String getName() {
    return TOOL_NAME;
  }

  private void registerIcons() {
    registerEmptyFolderTutorial(TYPE_ROOT);
    // registerTutorial(TYPE_ROOM);
    registerContentTypeIcon(TYPE_ROOT, icons.chats());
    registerContentTypeIcon(TYPE_ROOM, res.groupChat());
    final String emptyMsg = i18n.tWithNT("See the archive of old conversations"
        + TextUtils.IN_DEVELOPMENT_P, "with Brackets");
    registerEmptyMessages(TYPE_ROOM, emptyMsg);
    registerEmptyMessagesNotLogged(TYPE_ROOM, emptyMsg);
  }

}
