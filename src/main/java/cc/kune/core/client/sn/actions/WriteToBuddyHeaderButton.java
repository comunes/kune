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
package cc.kune.core.client.sn.actions;

import cc.kune.chat.client.ChatClient;
import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateChangedEvent;
import cc.kune.core.client.state.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ws.entheader.EntityHeader;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.google.inject.Inject;

public class WriteToBuddyHeaderButton {

  @Inject
  public WriteToBuddyHeaderButton(final WriteToAction writeToAction, final EntityHeader entityHeader,
      final StateManager stateManager, final Session session, final ChatClient chatEngine) {
    final ButtonDescriptor button = new ButtonDescriptor(writeToAction);
    // button.setVisible(false);
    button.withText("Write to your buddy");
    // button.setStyles("k-chat-add-as-buddie");
    writeToAction.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(AbstractAction.ENABLED)) {
          button.setVisible((Boolean) event.getNewValue());
        }
      }
    });
    entityHeader.addAction(button);
    stateManager.onStateChanged(true, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        final StateAbstractDTO state = event.getState();
        final GroupDTO group = state.getGroup();
        final String groupName = group.getShortName();
        final boolean imLogged = session.isLogged();
        final boolean isBuddie = chatEngine.isBuddy(groupName);
        if (imLogged && group.isPersonal() && isBuddie
            && !session.getCurrentUser().getShortName().equals(groupName)) {
          button.setTarget(group);
          writeToAction.setEnabled(true);
        } else {
          writeToAction.setEnabled(false);
        }
      }
    });
  }
}