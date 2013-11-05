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
package cc.kune.common.client.msgs;

import cc.kune.common.client.notify.NotifyLevel;

public class UserMessagesPresenter {

  public interface UserMessagesView {
    UserMessage add(NotifyLevel level, String title, String message, String id, Boolean closable,
        CloseCallback callback);
  }

  private boolean currentClosable;
  private String currentId;
  private NotifyLevel currentLevel;
  private UserMessage currentMsg;
  private String currentTitle;
  private UserMessagesView view;

  public UserMessagesPresenter() {
  }

  public UserMessage add(final NotifyLevel level, final String title, final String message,
      final String id, final boolean closable, final CloseCallback closeCallback) {
    if ((currentMsg != null && !currentMsg.isAttached()) || !level.equals(currentLevel)
        || !same(title, currentTitle) || !same(id, currentId) || closable != currentClosable) {
      currentMsg = view.add(level, title, message, id, closable, closeCallback);
    } else {
      // Similar message, so, I'll reuse the widget
      assert (currentMsg != null);
      currentMsg.appendMsg(message);
    }
    currentLevel = level;
    currentTitle = title;
    currentId = id;
    currentClosable = closable;
    return currentMsg;
  }

  public void init(final UserMessagesView view) {
    this.view = view;
  }

  private boolean same(final Object object, final Object otherObj) {
    if (object == otherObj) {
      return true;
    }
    if (otherObj == null) {
      return false;
    }
    if (object == null) {
      if (otherObj != null) {
        return false;
      }
    } else if (!object.equals(otherObj)) {
      return false;
    }
    return true;
  }
}
