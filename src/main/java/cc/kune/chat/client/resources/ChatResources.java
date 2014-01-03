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
package cc.kune.chat.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

// TODO: Auto-generated Javadoc
/**
 * The Interface ChatResources.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface ChatResources extends ClientBundle {
  
  /**
   * The Interface ChatIconCssResource.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface ChatIconCssResource extends CssResource {
  }

  /**
   * Away.
   *
   * @return the image resource
   */
  @Source("away.png")
  ImageResource away();

  /**
   * Busy.
   *
   * @return the image resource
   */
  @Source("busy.png")
  ImageResource busy();

  /**
   * Chat.
   *
   * @return the image resource
   */
  @Source("chat.png")
  ImageResource chat();

  /**
   * Chat blink.
   *
   * @return the image resource
   */
  @Source("chat-blink.gif")
  ImageResource chatBlink();

  /**
   * Chat no blink.
   *
   * @return the image resource
   */
  @Source("chat-no-blink.png")
  ImageResource chatNoBlink();

  /**
   * Group chat.
   *
   * @return the image resource
   */
  @Source("group-chat.png")
  ImageResource groupChat();

  /**
   * New chat.
   *
   * @return the image resource
   */
  @Source("new-chat.png")
  ImageResource newChat();

  /**
   * Offline.
   *
   * @return the image resource
   */
  @Source("offline.png")
  ImageResource offline();

  /**
   * Online.
   *
   * @return the image resource
   */
  @Source("online.png")
  ImageResource online();

  /**
   * Xa.
   *
   * @return the image resource
   */
  @Source("xa.png")
  ImageResource xa();
}
