/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.chat.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface ChatResources extends ClientBundle {
  public interface ChatIconCssResource extends CssResource {
  }

  @Source("away.png")
  ImageResource away();

  @Source("busy.png")
  ImageResource busy();

  @Source("chat.png")
  ImageResource chat();

  @Source("chat-blink.gif")
  ImageResource chatBlink();

  @Source("chat-no-blink.png")
  ImageResource chatNoBlink();

  @Source("group-chat.png")
  ImageResource groupChat();

  @Source("new-chat.png")
  ImageResource newChat();

  @Source("offline.png")
  ImageResource offline();

  @Source("online.png")
  ImageResource online();

  @Source("xa.png")
  ImageResource xa();
}
