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
import org.ourproject.kune.platf.client.dto.ContentDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.TagResultDTO;
import org.ourproject.kune.platf.client.errors.DefaultException;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ContentService extends RemoteService {

    void addAuthor(String userHash, StateToken token, String authorShortName) throws DefaultException;

    CommentDTO addComment(String userHash, StateToken token, Long parentCommentId, String commentText)
	    throws DefaultException;

    CommentDTO addComment(String userHash, StateToken token, String commentText) throws DefaultException;

    StateDTO addContent(String user, StateToken parentToken, String name) throws DefaultException;

    StateDTO addFolder(String hash, StateToken parentToken, String typeId, String title) throws DefaultException;

    StateDTO addRoom(String user, StateToken parentToken, String name) throws DefaultException;

    void delContent(String userHash, StateToken token) throws DefaultException;

    StateDTO getContent(String userHash, StateToken token) throws DefaultException;

    List<TagResultDTO> getSummaryTags(String userHash, StateToken groupToken) throws DefaultException;

    CommentDTO markCommentAsAbuse(String userHash, StateToken token, Long commentId) throws DefaultException;

    void rateContent(String userHash, StateToken token, Double value) throws DefaultException;

    void removeAuthor(String userHash, StateToken token, String authorShortName) throws DefaultException;

    String renameContainer(String userHash, StateToken token, String newName) throws DefaultException;

    String renameContent(String userHash, StateToken token, String newName) throws DefaultException;

    Integer save(String user, StateToken token, String content) throws DefaultException;

    ContentDTO setAsDefaultContent(String userHash, StateToken token);

    I18nLanguageDTO setLanguage(String userHash, StateToken token, String languageCode) throws DefaultException;

    void setPublishedOn(String userHash, StateToken token, Date date) throws DefaultException;

    List<TagResultDTO> setTags(String userHash, StateToken token, String tags) throws DefaultException;

    CommentDTO voteComment(String userHash, StateToken token, Long commentId, boolean votePositive)
	    throws DefaultException;
}
