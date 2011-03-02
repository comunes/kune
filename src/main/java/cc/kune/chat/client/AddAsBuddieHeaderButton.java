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
package cc.kune.chat.client;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.noti.NotifyUser;
import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateChangedEvent;
import cc.kune.core.client.state.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ws.entheader.EntityHeader;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.im.client.roster.events.RosterGroupChangedEvent;
import com.calclab.emite.im.client.roster.events.RosterGroupChangedHandler;
import com.calclab.suco.client.Suco;
import com.google.inject.Inject;

public class AddAsBuddieHeaderButton {

    public static class AddAsBuddieAction extends AbstractExtendedAction {
        private final ChatClient chatEngine;
        private final Session session;

        @Inject
        public AddAsBuddieAction(final ChatClient chatEngine, final Session session, final StateManager stateManager,
                final I18nTranslationService i18n, final IconResources img) {
            super();
            this.chatEngine = chatEngine;
            this.session = session;
            stateManager.onStateChanged(true, new StateChangedHandler() {
                @Override
                public void onStateChanged(final StateChangedEvent event) {
                    setState(event.getState());
                }
            });
            Suco.get(XmppRoster.class).addRosterGroupChangedHandler(new RosterGroupChangedHandler() {

                @Override
                public void onGroupChanged(final RosterGroupChangedEvent event) {
                    setState(session.getCurrentState());
                }
            });
            putValue(Action.NAME, i18n.t("Add as a buddie"));
            putValue(Action.SMALL_ICON, img.addGreen());
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            chatEngine.addNewBuddie(session.getCurrentState().getGroup().getShortName());
            NotifyUser.info("Added as buddie. Waiting buddie response");
            setEnabled(false);
        }

        private boolean currentGroupsIsAsPerson(final StateAbstractDTO state) {
            return state.getGroup().isPersonal();
        }

        private boolean isNotMe(final String groupName) {
            return !session.getCurrentUser().getShortName().equals(groupName);
        }

        private void setState(final StateAbstractDTO state) {
            final String groupName = state.getGroup().getShortName();
            final boolean imLogged = session.isLogged();
            final boolean isNotBuddie = !chatEngine.isBuddie(groupName);
            if (imLogged && currentGroupsIsAsPerson(state) && isNotBuddie && isNotMe(groupName)) {
                setEnabled(true);
            } else {
                setEnabled(false);
            }
        }
    }

    @Inject
    public AddAsBuddieHeaderButton(final AddAsBuddieAction buddieAction, final EntityHeader entityHeader) {
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