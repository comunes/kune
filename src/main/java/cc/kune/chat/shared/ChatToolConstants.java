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
package cc.kune.chat.shared;

import cc.kune.common.shared.res.KuneIcon;

// TODO: Auto-generated Javadoc
/**
 * The Class ChatToolConstants.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class ChatToolConstants {

  /** The Constant ICON_TYPE_CHAT. */
  public static final KuneIcon ICON_TYPE_CHAT = new KuneIcon('d');
  
  /** The Constant ICON_TYPE_ROOM. */
  public static final KuneIcon ICON_TYPE_ROOM = new KuneIcon('d');
  
  /** The Constant ICON_TYPE_ROOT. */
  public static final KuneIcon ICON_TYPE_ROOT = new KuneIcon('d');
  
  /** The Constant ROOT_NAME. */
  public static final String ROOT_NAME = "chats";
  
  /** The Constant TOOL_NAME. */
  public static final String TOOL_NAME = "chats";
  
  /** The Constant TYPE_CHAT. */
  public static final String TYPE_CHAT = TOOL_NAME + "." + "chat";
  
  /** The Constant TYPE_ROOM. */
  public static final String TYPE_ROOM = TOOL_NAME + "." + "room";
  
  /** The Constant TYPE_ROOT. */
  public static final String TYPE_ROOT = TOOL_NAME + "." + "root";

  /**
   * Instantiates a new chat tool constants.
   */
  private ChatToolConstants() {
  }
}
