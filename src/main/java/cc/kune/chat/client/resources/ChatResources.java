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
package cc.kune.chat.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface ChatResources extends ClientBundle {
  public interface ChatIconCssResource extends CssResource {
  }

  @Source("add.png")
  ImageResource add();

  @Source("away.png")
  ImageResource away();

  @Source("busy.png")
  ImageResource busy();

  @Source("cancel.png")
  ImageResource cancel();

  @Source("chat.png")
  ImageResource chat();

  @Source("e-icon-a.gif")
  ImageResource chatBlink();

  @Source("chat-new-message-small.png")
  ImageResource chatNewMessageSmall();

  @Source("e-icon.png")
  ImageResource chatNoBlink();

  @Source("chat-icons.css")
  ChatIconCssResource css();

  @Source("del.png")
  ImageResource del();

  @Source("group-chat.png")
  ImageResource groupChat();

  @Source("info.png")
  ImageResource info();

  @Source("info-lamp.png")
  ImageResource infoLamp();

  @Source("invisible.png")
  ImageResource invisible();

  @Source("message.png")
  ImageResource message();

  @Source("new-chat.png")
  ImageResource newChat();

  @Source("new-email.png")
  ImageResource newEmail();

  @Source("new-message.png")
  ImageResource newMessage();

  @Source("not-authorized.png")
  ImageResource notAuthorized();

  @Source("offline.png")
  ImageResource offline();

  @Source("online.png")
  ImageResource online();

  @Source("question.png")
  ImageResource question();

  @Source("room-new-message-small.png")
  ImageResource roomNewMessageSmall();

  @Source("room-small.png")
  ImageResource roomSmall();

  @Source("unavailable.png")
  ImageResource unavailable();

  @Source("user_add.png")
  ImageResource userAdd();

  @Source("xa.png")
  ImageResource xa();
}