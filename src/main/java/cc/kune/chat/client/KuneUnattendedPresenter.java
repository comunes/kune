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

import cc.kune.chat.client.ChatClientDefault.ChatClientAction;

import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.calclab.hablar.signals.client.SignalPreferences;
import com.calclab.hablar.signals.client.unattended.UnattendedChatsChangedEvent;
import com.calclab.hablar.signals.client.unattended.UnattendedChatsChangedHandler;
import com.calclab.hablar.signals.client.unattended.UnattendedPagesManager;

/**
 * Handles the presentation of unattended chats
 */
public class KuneUnattendedPresenter {
    private boolean active;

    public KuneUnattendedPresenter(final HablarEventBus hablarEventBus, final SignalPreferences preferences,
            final UnattendedPagesManager unattendedManager, final ChatClientAction action) {
        active = false;
        hablarEventBus.addHandler(UnattendedChatsChangedEvent.TYPE, new UnattendedChatsChangedHandler() {
            @Override
            public void handleUnattendedChatChange(final UnattendedChatsChangedEvent event) {
                final int unattendedChatsCount = unattendedManager.getSize();
                if (unattendedChatsCount > 0 && active == false) {
                    active = true;
                    action.setBlink(true);
                } else if (unattendedChatsCount == 0 && active == true) {
                    action.setBlink(false);
                    active = false;
                }
            }

        });
    }
}
