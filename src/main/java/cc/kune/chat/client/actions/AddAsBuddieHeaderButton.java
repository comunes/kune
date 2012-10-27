/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.chat.client.actions;

import cc.kune.chat.client.ChatClient;
import cc.kune.chat.client.ChatInstances;
import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
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

import com.calclab.emite.im.client.roster.events.RosterItemChangedEvent;
import com.calclab.emite.im.client.roster.events.RosterItemChangedHandler;
import com.calclab.emite.im.client.roster.events.RosterRetrievedEvent;
import com.calclab.emite.im.client.roster.events.RosterRetrievedHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class AddAsBuddieHeaderButton {

  public static class AddAsBuddieHeaderAction extends AbstractExtendedAction {
    private final ChatClient chatEngine;
    private final Session session;
    private final Provider<SocialNetServiceAsync> snService;

    @Inject
    public AddAsBuddieHeaderAction(final ChatClient chatEngine, final Session session,
        final ChatInstances chatInstances, final StateManager stateManager,
        final I18nTranslationService i18n, final IconicResources img,
        final Provider<SocialNetServiceAsync> snService) {
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
      chatInstances.roster.addRosterItemChangedHandler(new RosterItemChangedHandler() {
        @Override
        public void onRosterItemChanged(final RosterItemChangedEvent event) {
          setState();
        }
      });
      chatInstances.roster.addRosterRetrievedHandler(new RosterRetrievedHandler() {
        @Override
        public void onRosterRetrieved(final RosterRetrievedEvent event) {
          setState();
        }
      });
      putValue(Action.NAME, i18n.t(CoreMessages.ADD_AS_A_BUDDY));
      putValue(Action.SMALL_ICON, img.add());
    }

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

    private boolean currentGroupsIsAsPerson(final StateAbstractDTO state) {
      return state.getGroup().isPersonal();
    }

    private boolean isNotMe(final String groupName) {
      return !session.getCurrentUser().getShortName().equals(groupName);
    }

    private void setState() {
      final StateAbstractDTO currentState = session.getCurrentState();
      if (currentState != null) {
        setState(currentState);
      }
    }

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

  @Inject
  public AddAsBuddieHeaderButton(final AddAsBuddieHeaderAction buddieAction,
      final EntityHeader entityHeader) {
    final ButtonDescriptor button = new ButtonDescriptor(buddieAction);
    button.setVisible(false);
    button.setStyles("k-chat-add-as-buddie");
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
