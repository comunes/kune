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
package org.ourproject.kune.workspace.client.oldsn.other;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.platf.client.actions.OldAbstractAction;
import org.ourproject.kune.platf.client.actions.AbstractExtendedAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeListener;
import org.ourproject.kune.platf.client.actions.ui.ButtonDescriptor;
import org.ourproject.kune.platf.client.ui.noti.OldNotifyUser;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;

import cc.kune.core.client.resources.icons.IconConstants;
import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;

public class AddAsBuddieHeaderButton {

    public static class AddAsBuddieAction extends AbstractExtendedAction {
        private final Provider<ChatEngine> chatEngine;
        private final Session session;

        public AddAsBuddieAction(final Provider<ChatEngine> chatEngine, final Session session,
                final StateManager stateManager, final I18nTranslationService i18n, final IconResources img) {
            super();
            this.chatEngine = chatEngine;
            this.session = session;
            stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
                public void onEvent(final StateAbstractDTO state) {
                    setState(state);
                }
            });
            chatEngine.get().addOnRosterChanged(new Listener0() {
                public void onEvent() {
                    setState(session.getCurrentState());
                }
            });
            putValue(Action.NAME, i18n.t("Add as a buddie"));
            putValue(Action.SMALL_ICON, IconConstants.toPath(img.addGreen()));
        }

        public void actionPerformed(final ActionEvent event) {
            chatEngine.get().addNewBuddie(session.getCurrentState().getGroup().getShortName());
            OldNotifyUser.info("Added as buddie. Waiting buddie response");
            setEnabled(false);
        }

        private void setState(final StateAbstractDTO state) {
            final String groupName = state.getGroup().getShortName();
            final boolean isPersonal = state.getGroup().isPersonal();
            final boolean isLogged = session.isLogged();
            if (isLogged && isPersonal && (!chatEngine.get().isBuddie(groupName))
                    && (!session.getCurrentUser().getShortName().equals(groupName))) {
                setEnabled(true);
            } else {
                setEnabled(false);
            }
        }
    }

    public AddAsBuddieHeaderButton(final Provider<ChatEngine> chatEngine, final Session session,
            final StateManager stateManager, final I18nTranslationService i18n, final IconResources img,
            final EntityHeader entityHeader) {
        final AddAsBuddieAction buddieAction = new AddAsBuddieAction(chatEngine, session, stateManager, i18n, img);
        final ButtonDescriptor button = new ButtonDescriptor(buddieAction);
        button.setVisible(false);
        buddieAction.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(OldAbstractAction.ENABLED)) {
                    button.setVisible((Boolean) event.getNewValue());
                }
            }
        });
        entityHeader.addAction(button);
    }
}