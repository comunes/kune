/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.chat.client.actions;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.platf.client.dto.UserDTO;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;

public class ChatLoginAction extends WorkspaceAction {
    private final ChatEngine engine;

    public ChatLoginAction(final ChatEngine engine) {
	this.engine = engine;
    }

    public void execute(final Object value, final Object extra) {
	login((UserDTO) value);
    }

    private void login(final UserDTO user) {
	engine.login(user.getChatName(), user.getChatPassword());
    }
}
