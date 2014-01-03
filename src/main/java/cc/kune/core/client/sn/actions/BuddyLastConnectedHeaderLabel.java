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
package cc.kune.core.client.sn.actions;

import cc.kune.chat.client.ChatOptions;
import cc.kune.chat.client.LastConnectedManager;
import cc.kune.chat.client.actions.StartChatWithMemberAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.core.client.contacts.SimpleContactManager;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ws.entheader.EntityHeader;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.im.client.roster.events.RosterItemChangedEvent;
import com.calclab.emite.im.client.roster.events.RosterItemChangedHandler;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class BuddyLastConnectedHeaderLabel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BuddyLastConnectedHeaderLabel {

  /** The button. */
  private final ButtonDescriptor button;

  /** The last connected manager. */
  private final LastConnectedManager lastConnectedManager;

  /**
   * Instantiates a new buddy last connected header label.
   * 
   * @param chatAction
   *          the chat action
   * @param entityHeader
   *          the entity header
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   * @param simpleContactManager
   *          the simple contact manager
   * @param lastConnectedManager
   *          the last connected manager
   * @param roster
   *          the roster
   * @param chatOptions
   *          the chat options
   */
  @Inject
  public BuddyLastConnectedHeaderLabel(final StartChatWithMemberAction chatAction,
      final EntityHeader entityHeader, final StateManager stateManager, final Session session,
      final SimpleContactManager simpleContactManager, final LastConnectedManager lastConnectedManager,
      final XmppRoster roster, final ChatOptions chatOptions) {
    this.lastConnectedManager = lastConnectedManager;
    button = new ButtonDescriptor(chatAction);
    button.setStyles("k-buddy-last-connected");
    chatAction.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(Action.ENABLED)) {
          button.setVisible((Boolean) event.getNewValue());
        }
      }
    });
    roster.addRosterItemChangedHandler(new RosterItemChangedHandler() {
      @Override
      public void onRosterItemChanged(final RosterItemChangedEvent event) {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
          @Override
          public void execute() {
            final Object target = button.getTarget();
            if (target instanceof GroupDTO) {
              final String username = ((GroupDTO) target).getShortName();
              if (event.getRosterItem().getJID().equals(chatOptions.uriFrom(username))) {
                // Ok, is this user, we set the last connected info
                setLabelText(username);
              }
            }
          }

        });
      }
    });
    chatAction.setEnabled(false);
    entityHeader.addAction(button);
    stateManager.onStateChanged(true, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        final StateAbstractDTO state = event.getState();
        final GroupDTO group = state.getGroup();
        final String groupName = group.getShortName();
        final boolean imLogged = session.isLogged();
        final boolean isBuddie = simpleContactManager.isBuddy(groupName);
        if (imLogged && group.isPersonal() && isBuddie
            && !session.getCurrentUser().getShortName().equals(groupName)) {
          button.setTarget(group);
          setLabelText(groupName);
          chatAction.setEnabled(true);
        } else {
          chatAction.setEnabled(false);
        }
      }
    });
  }

  /**
   * Sets the label text.
   * 
   * @param username
   *          the new label text
   */
  private void setLabelText(final String username) {
    button.withText(lastConnectedManager.get(username, true));
  }
}
