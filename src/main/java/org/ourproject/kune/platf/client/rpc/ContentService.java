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

package org.ourproject.kune.platf.client.rpc;

import java.util.Date;
import java.util.List;

import org.ourproject.kune.platf.client.dto.CommentDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.TagResultDTO;
import org.ourproject.kune.platf.client.errors.DefaultException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface ContentService extends RemoteService {

    public static class App {
        private static ContentServiceAsync instance;

        public static ContentServiceAsync getInstance() {
            if (instance == null) {
                instance = (ContentServiceAsync) GWT.create(ContentService.class);
                ((ServiceDefTarget) instance).setServiceEntryPoint(GWT.getModuleBaseURL() + "ContentService");

            }
            return instance;
        }
    }

    void addAuthor(String userHash, String groupShortName, String documentId, String authorShortName)
            throws DefaultException;

    CommentDTO addComment(String userHash, String groupShortName, String documentId, Long parentCommentId,
            String commentText) throws DefaultException;

    CommentDTO addComment(String userHash, String groupShortName, String documentId, String commentText)
            throws DefaultException;

    StateDTO addContent(String user, String groupShortName, Long parentFolderId, String name) throws DefaultException;

    StateDTO addFolder(String hash, String groupShortName, Long parentFolderId, String title) throws DefaultException;

    StateDTO addRoom(String user, String groupShortName, Long parentFolderId, String name) throws DefaultException;

    void delContent(String userHash, String groupShortName, String documentId) throws DefaultException;

    StateDTO getContent(String userHash, String groupShortName, StateToken token) throws DefaultException;

    List<TagResultDTO> getSummaryTags(String userHash, String groupShortName) throws DefaultException;

    CommentDTO markCommentAsAbuse(String userHash, String groupShortName, String documentId, Long commentId)
            throws DefaultException;

    void rateContent(String userHash, String groupShortName, String documentId, Double value) throws DefaultException;

    void removeAuthor(String userHash, String groupShortName, String documentId, String authorShortName)
            throws DefaultException;

    String rename(String userHash, String groupShortName, String token, String newName) throws DefaultException;

    Integer save(String user, String groupShortName, String documentId, String content) throws DefaultException;

    I18nLanguageDTO setLanguage(String userHash, String groupShortName, String documentId, String languageCode)
            throws DefaultException;

    void setPublishedOn(String userHash, String groupShortName, String documentId, Date date) throws DefaultException;

    List<TagResultDTO> setTags(String userHash, String groupShortName, String documentId, String tags)
            throws DefaultException;

    CommentDTO voteComment(String userHash, String groupShortName, String documentId, Long commentId,
            boolean votePositive) throws DefaultException;
}
