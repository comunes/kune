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
package cc.kune.chat.client.actions;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.core.client.ws.entheader.EntityHeader;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenGroupPublicChatRoomButton.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class OpenGroupPublicChatRoomButton {

  /**
   * Instantiates a new open group public chat room button.
   *
   * @param openRoomAction the open room action
   * @param entityHeader the entity header
   */
  @Inject
  public OpenGroupPublicChatRoomButton(final OpenGroupPublicChatRoomAction openRoomAction,
      final EntityHeader entityHeader) {
    final ButtonDescriptor button = new ButtonDescriptor(openRoomAction);
    openRoomAction.setInviteMembers(false);
    button.setVisible(false);
    button.setStyles(ActionStyles.BTN_NO_BACK_NO_BORDER);
    openRoomAction.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(AbstractAction.ENABLED)) {
          button.setVisible((Boolean) event.getNewValue());
        }
      }
    });
    entityHeader.addAction(button);
  }
}
