/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.chat.client.actions.conditions;

import cc.kune.chat.client.ChatClient;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiAddCondition;
import cc.kune.common.client.errors.UIException;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class IsBuddieCondition implements GuiAddCondition {

  private final ChatClient chatClient;
  private final Session session;

  @Inject
  public IsBuddieCondition(final Session session, final ChatClient chatClient) {
    this.chatClient = chatClient;
    this.session = session;
  }

  private boolean isBuddie(final String targetName) {
    return chatClient.isBuddy(targetName);
  }

  private boolean isThisGroupInRoster(final GuiActionDescrip descr) {
    final String targetName = ((GroupDTO) descr.getTarget()).getShortName();
    return isBuddie(targetName);
  }

  private boolean isThisPersonInRoster(final GuiActionDescrip descr) {
    final String targetName = ((UserSimpleDTO) descr.getTarget()).getShortName();
    return isBuddie(targetName);
  }

  @Override
  public boolean mustBeAdded(final GuiActionDescrip descr) {
    if (session.isNotLogged()) {
      return false;
    }
    if (descr.getTarget() instanceof UserSimpleDTO) {
      return isThisPersonInRoster(descr);
    } else if (descr.getTarget() instanceof GroupDTO) {
      return isThisGroupInRoster(descr);
    } else {
      throw new UIException("Unsupported target");
    }
  }
}
