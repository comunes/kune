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

import cc.kune.chat.client.ChatClient;
import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.SocialNetServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ws.entheader.EntityHeader;
import cc.kune.core.shared.dto.StateAbstractDTO;

import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.im.client.roster.events.RosterItemChangedEvent;
import com.calclab.emite.im.client.roster.events.RosterItemChangedHandler;
import com.calclab.emite.im.client.roster.events.RosterRetrievedEvent;
import com.calclab.emite.im.client.roster.events.RosterRetrievedHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class AddAsBuddieHeaderButton.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AddAsBuddieHeaderButton {

  /**
   * The Class AddAsBuddieHeaderAction.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class AddAsBuddieHeaderAction extends AbstractExtendedAction {
    
    /** The chat engine. */
    private final ChatClient chatEngine;
    
    /** The session. */
    private final Session session;
    
    /** The sn service. */
    private final Provider<SocialNetServiceAsync> snService;

    /**
     * Instantiates a new adds the as buddie header action.
     *
     * @param chatEngine the chat engine
     * @param session the session
     * @param roster the roster
     * @param stateManager the state manager
     * @param i18n the i18n
     * @param img the img
     * @param snService the sn service
     */
    @Inject
    public AddAsBuddieHeaderAction(final ChatClient chatEngine, final Session session,
        final XmppRoster roster, final StateManager stateManager, final I18nTranslationService i18n,
        final IconicResources img, final Provider<SocialNetServiceAsync> snService) {
      super();
      this.chatEngine = chatEngine;
      this.session = session;
      this.snService = snService;
      stateManager.onStateChanged(true, new StateChangedHandler() {
        @Override
        public void onStateChanged(final StateChangedEvent event) {
          setState(event.getState());
        }
      });
      roster.addRosterItemChangedHandler(new RosterItemChangedHandler() {
        @Override
        public void onRosterItemChanged(final RosterItemChangedEvent event) {
          setState();
        }
      });
      roster.addRosterRetrievedHandler(new RosterRetrievedHandler() {
        @Override
        public void onRosterRetrieved(final RosterRetrievedEvent event) {
          setState();
        }
      });
      putValue(Action.NAME, i18n.t(CoreMessages.ADD_AS_A_BUDDY));
      putValue(Action.SMALL_ICON, img.add());
    }

    /* (non-Javadoc)
     * @see cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      final String username = session.getCurrentState().getGroup().getShortName();
      chatEngine.addNewBuddy(username);
      setEnabled(false);
      snService.get().addAsBuddie(session.getUserHash(), username, new AsyncCallbackSimple<Void>() {
        @Override
        public void onSuccess(final Void result) {
        }
      });
    }

    /**
     * Current groups is as person.
     *
     * @param state the state
     * @return true, if successful
     */
    private boolean currentGroupsIsAsPerson(final StateAbstractDTO state) {
      return state.getGroup().isPersonal();
    }

    /**
     * Checks if is not me.
     *
     * @param groupName the group name
     * @return true, if is not me
     */
    private boolean isNotMe(final String groupName) {
      return !session.getCurrentUser().getShortName().equals(groupName);
    }

    /**
     * Sets the state.
     */
    private void setState() {
      final StateAbstractDTO currentState = session.getCurrentState();
      if (currentState != null) {
        setState(currentState);
      }
    }

    /**
     * Sets the state.
     *
     * @param state the new state
     */
    private void setState(final StateAbstractDTO state) {
      final String groupName = state.getGroup().getShortName();
      final boolean imLogged = session.isLogged();
      final boolean isNotBuddie = !chatEngine.isBuddy(groupName);
      if (imLogged && currentGroupsIsAsPerson(state) && isNotBuddie && isNotMe(groupName)) {
        setEnabled(true);
      } else {
        setEnabled(false);
      }
    }
  }

  /**
   * Instantiates a new adds the as buddie header button.
   *
   * @param buddieAction the buddie action
   * @param entityHeader the entity header
   */
  @Inject
  public AddAsBuddieHeaderButton(final AddAsBuddieHeaderAction buddieAction,
      final EntityHeader entityHeader) {
    final ButtonDescriptor button = new ButtonDescriptor(buddieAction);
    button.setVisible(false);
    button.setStyles(ActionStyles.BTN_NO_BACK_NO_BORDER);
    buddieAction.addPropertyChangeListener(new PropertyChangeListener() {
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
