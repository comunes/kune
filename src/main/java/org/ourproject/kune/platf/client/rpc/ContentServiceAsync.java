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

import java.util.Date;

import org.ourproject.kune.platf.client.dto.StateToken;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContentServiceAsync {

    void addContent(String user, String groupShortName, Long parentFolderId, String name, AsyncCallback callback);

    void getContent(String user, String groupShortName, StateToken newState, AsyncCallback callback);

    void addFolder(String hash, String groupShortName, Long parentFolderId, String title, AsyncCallback callback);

    void addRoom(String user, String groupShortName, Long parentFolderId, String name, AsyncCallback callback);

    void save(String user, String groupShortName, String documentId, String content, AsyncCallback asyncCallback);

    void rateContent(String userHash, String groupShortName, String documentId, Double value,
            AsyncCallback asyncCallback);

    void setLanguage(String userHash, String groupShortName, String documentId, String languageCode,
            AsyncCallback asyncCallback);

    void setPublishedOn(String userHash, String groupShortName, String documentId, Date publishedOn,
            AsyncCallback asyncCallback);

    void setTags(String userHash, String groupShortName, String documentId, String tags, AsyncCallback asyncCallback);

    void addAuthor(String userHash, String groupShortName, String documentId, String authorShortName,
            AsyncCallback asyncCallback);

    void removeAuthor(String userHash, String groupShortName, String documentId, String authorShortName,
            AsyncCallback asyncCallback);

    void renameContent(String userHash, String groupShortName, String documentId, String newTitle,
            AsyncCallback asyncCallback);

    void renameFolder(String hash, String groupShortName, Long folderId, String title, AsyncCallback asyncCallback);

    void delContent(String userHash, String groupShortName, String documentId, AsyncCallback asyncCallback);

}
