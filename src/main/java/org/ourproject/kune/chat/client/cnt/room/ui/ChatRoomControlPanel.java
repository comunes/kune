/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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

package org.ourproject.kune.chat.client.cnt.room.ui;

import org.ourproject.kune.chat.client.cnt.room.ChatRoomControlView;
import org.ourproject.kune.chat.client.cnt.room.ChatRoomListener;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.CustomPushButton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChatRoomControlPanel extends HorizontalPanel implements ChatRoomControlView {
    private final CustomPushButton enterRoomBtn;

    public ChatRoomControlPanel(final ChatRoomListener listener, final I18nTranslationService i18n) {
        enterRoomBtn = new CustomPushButton(i18n.t("Enter room"), new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onEnterRoom();
            }
        });
        add(enterRoomBtn);
        setEnterRoomEnabled(true);
    }

    public void setEnterRoomEnabled(final boolean isEnabled) {
        enterRoomBtn.setEnabled(isEnabled);
    }

}