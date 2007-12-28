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
import org.ourproject.kune.platf.client.errors.AccessViolationException;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.client.errors.GroupNotFoundException;
import org.ourproject.kune.platf.client.errors.I18nNotFoundException;
import org.ourproject.kune.platf.client.errors.UserNotFoundException;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface ContentService extends RemoteService {

    StateDTO getContent(String userHash, String groupShortName, StateToken token) throws ContentNotFoundException,
            AccessViolationException, GroupNotFoundException;

    int save(String user, String groupShortName, String documentId, String content) throws AccessViolationException,
            ContentNotFoundException;

    StateDTO addContent(String user, String groupShortName, Long parentFolderId, String name)
            throws AccessViolationException, ContentNotFoundException;

    StateDTO addFolder(String hash, String groupShortName, Long parentFolderId, String title)
            throws ContentNotFoundException, AccessViolationException, GroupNotFoundException;

    StateDTO addRoom(String user, String groupShortName, Long parentFolderId, String name)
            throws AccessViolationException, ContentNotFoundException, GroupNotFoundException;

    void rateContent(String userHash, String groupShortName, String documentId, Double value)
            throws ContentNotFoundException, AccessViolationException;

    void setLanguage(String userHash, String groupShortName, String documentId, String languageCode)
            throws ContentNotFoundException, I18nNotFoundException;

    void setPublishedOn(String userHash, String groupShortName, String documentId, Date date)
            throws ContentNotFoundException;

    void setTags(String userHash, String groupShortName, String documentId, String tags)
            throws ContentNotFoundException;

    void addAuthor(String userHash, String groupShortName, String documentId, String authorShortName)
            throws ContentNotFoundException, UserNotFoundException;

    void removeAuthor(String userHash, String groupShortName, String documentId, String authorShortName)
            throws ContentNotFoundException, UserNotFoundException;

    void setTitle(String userHash, String groupShortName, String documentId, String newTitle)
            throws ContentNotFoundException;

    void delContent(String userHash, String groupShortName, String documentId) throws ContentNotFoundException;

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
