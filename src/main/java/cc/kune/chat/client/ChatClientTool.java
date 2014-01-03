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
package cc.kune.chat.client;

import static cc.kune.chat.shared.ChatToolConstants.*;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.state.HistoryWrapper;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.gspace.client.tool.FoldableAbstractClientTool;
import cc.kune.gspace.client.tool.selector.ToolSelector;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class ChatClientTool.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ChatClientTool extends FoldableAbstractClientTool {

  /**
   * Instantiates a new chat client tool.
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
  public ChatClientTool(final I18nTranslationService i18n, final ToolSelector toolSelector,
      final ContentCapabilitiesRegistry cntCapRegistry, final HistoryWrapper history) {
    // FIXME: change this in ChatConstants and in db via migration
    super(
        TOOL_NAME,
        i18n.t("chatrooms"),
        i18n.t(
            "A 'room' where you can have a group-chat with many users at once. Rooms can be public or private. Users can be from [%s] or other sites (compatible with Gmail chat)",
            i18n.getSiteCommonName()), ICON_TYPE_ROOT, AccessRolDTO.Viewer, toolSelector,
        cntCapRegistry, i18n, history);
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
    // registerTutorial(TYPE_ROOM);
    registerContentTypeIcon(TYPE_ROOT, ICON_TYPE_ROOT);
    registerContentTypeIcon(TYPE_ROOM, ICON_TYPE_ROOM);
    final String emptyMsg = i18n.tWithNT("See the archive of old conversations"
        + TextUtils.IN_DEVELOPMENT_P, "with Brackets");
    registerEmptyMessages(TYPE_ROOM, emptyMsg);
    registerEmptyMessagesNotLogged(TYPE_ROOM, emptyMsg);
  }

}
