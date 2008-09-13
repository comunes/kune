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

package org.ourproject.kune.workspace.client.ctxnav;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

public interface ContextNavigator {

    void addAction(String contentTypeId, ActionDescriptor<StateToken> action);

    void editItem(StateToken stateToken);

    View getView();

    void registerContentTypeIcon(String contentTypeId, String iconUrl);

    void registerDraggableType(String typeId);

    void registerDroppableType(String typeId);

    void removeAction(String contentTypeId, ActionDescriptor<StateToken> action);

    void selectItem(StateToken stateToken);

    void setItemText(StateToken stateToken, String name);

    void setState(StateDTO state);

}
