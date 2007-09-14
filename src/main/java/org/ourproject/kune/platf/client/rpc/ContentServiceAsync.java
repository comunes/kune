/*
 *
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

package org.ourproject.kune.platf.client.rpc;

import org.ourproject.kune.platf.client.dto.StateToken;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContentServiceAsync {
    void addContent(String user, Long parentFolderId, String name, AsyncCallback callback);

    void getContent(String user, StateToken newState, AsyncCallback callback);

    void addFolder(String hash, String groupShortName, Long parentFolderId, String title, AsyncCallback callback);

    void addRoom(String user, String groupShortName, Long parentFolderId, String name, AsyncCallback callback);

    void save(String user, String documentId, String content, AsyncCallback asyncCallback);
}
