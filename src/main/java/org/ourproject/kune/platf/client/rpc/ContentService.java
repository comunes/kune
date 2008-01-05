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
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface ContentService extends RemoteService {

    StateDTO getContent(String userHash, String groupShortName, StateToken token) throws SerializableException;

    int save(String user, String groupShortName, String documentId, String content) throws SerializableException;

    StateDTO addContent(String user, String groupShortName, Long parentFolderId, String name)
            throws SerializableException;

    StateDTO addFolder(String hash, String groupShortName, Long parentFolderId, String title)
            throws SerializableException;

    StateDTO addRoom(String user, String groupShortName, Long parentFolderId, String name) throws SerializableException;

    void rateContent(String userHash, String groupShortName, String documentId, Double value)
            throws SerializableException;

    void setLanguage(String userHash, String groupShortName, String documentId, String languageCode)
            throws SerializableException;

    void setPublishedOn(String userHash, String groupShortName, String documentId, Date date)
            throws SerializableException;

    void setTags(String userHash, String groupShortName, String documentId, String tags) throws SerializableException;

    void addAuthor(String userHash, String groupShortName, String documentId, String authorShortName)
            throws SerializableException;

    void removeAuthor(String userHash, String groupShortName, String documentId, String authorShortName)
            throws SerializableException;

    void delContent(String userHash, String groupShortName, String documentId) throws SerializableException;

    String renameContent(String userHash, String groupShortName, String documentId, String newName)
            throws SerializableException;

    String renameFolder(String hash, String groupShortName, Long folderId, String newName) throws SerializableException;

    String rename(String userHash, String groupShortName, String token, String newName) throws SerializableException;

    public static class App {
        private static ContentServiceAsync instance;

        public static ContentServiceAsync getInstance() {
            if (instance == null) {
                instance = (ContentServiceAsync) GWT.create(ContentService.class);
                ((ServiceDefTarget) instance).setServiceEntryPoint(GWT.getModuleBaseURL() + "ContentService");

            }
            return instance;
        }

        public static void setMock(final ContentServiceMocked mock) {
            instance = mock;
        }
    }

}
