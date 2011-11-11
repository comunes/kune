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
package cc.kune.chat.client;

import cc.kune.chat.client.actions.AddAsBuddieHeaderButton;
import cc.kune.chat.client.actions.ChatClientActions;
import cc.kune.chat.client.actions.ChatSitebarActions;
import cc.kune.common.client.ExtendedGinModule;
import cc.kune.core.client.avatar.MediumAvatarDecorator;
import cc.kune.core.client.avatar.SmallAvatarDecorator;
import cc.kune.core.client.sn.SimpleContactManager;

public class ChatGinModule extends ExtendedGinModule {
  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.inject.client.AbstractGinModule#configure()
   */
  @Override
  protected void configure() {
    s(ChatInstances.class);
    bind(SmallAvatarDecorator.class).to(SmallAvatarDecoratorImpl.class);
    bind(MediumAvatarDecorator.class).to(MediumAvatarDecoratorImpl.class);
    s(ChatClient.class, ChatClientDefault.class);
    s(SimpleContactManager.class, ChatClient.class);
    s(ChatOptions.class);
    s(ChatSitebarActions.class);
    s(AddAsBuddieHeaderButton.class);
    // bind(OpenGroupPublicChatRoomButton.class);
    s(ChatClientTool.class);
    s(ChatClientActions.class);
  }
}
