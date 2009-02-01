/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

import org.ourproject.kune.platf.client.dto.CommentDTO;
import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.ContentStatusDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.RateResultDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.TagCloudResultDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContentServiceAsync {

    void addAuthor(String userHash, StateToken token, String authorShortName, AsyncCallback<?> asyncCallback);

    void addComment(String userHash, StateToken token, Long parentCommentId, String commentText,
            AsyncCallback<CommentDTO> asyncCallback);

    void addComment(String userHash, StateToken token, String commentText, AsyncCallback<CommentDTO> asyncCallback);

    void addContent(String user, StateToken parentToken, String name, String typeId,
            AsyncCallback<StateContentDTO> callback);

    void addFolder(String hash, StateToken parentToken, String title, String typeId,
            AsyncCallback<StateContainerDTO> callback);

    void addRoom(String user, StateToken parentToken, String name, AsyncCallback<StateContainerDTO> callback);

    void delContent(String userHash, StateToken token, AsyncCallback<StateContentDTO> asyncCallback);

    void getContent(String user, StateToken newState, AsyncCallback<StateAbstractDTO> callback);

    void getSummaryTags(String userHash, StateToken groupToken, AsyncCallback<TagCloudResultDTO> asyncCallback);

    void markCommentAsAbuse(String userHash, StateToken token, Long commentId, AsyncCallback<CommentDTO> asyncCallback);

    void rateContent(String userHash, StateToken token, Double value, AsyncCallback<RateResultDTO> asyncCallback);

    void removeAuthor(String userHash, StateToken token, String authorShortName, AsyncCallback<?> asyncCallback);

    void renameContainer(String userHash, StateToken token, String newName,
            AsyncCallback<StateAbstractDTO> asyncCallback);

    void renameContent(String userHash, StateToken token, String newName, AsyncCallback<StateAbstractDTO> asyncCallback);

    void save(String user, StateToken token, String content, AsyncCallback<?> asyncCallback);

    void setAsDefaultContent(String userHash, StateToken token, AsyncCallback<ContentSimpleDTO> asyncCallback);

    void setLanguage(String userHash, StateToken token, String languageCode,
            AsyncCallback<I18nLanguageDTO> asyncCallback);

    void setPublishedOn(String userHash, StateToken token, Date publishedOn, AsyncCallback<?> asyncCallback);

    void setStatus(String userHash, StateToken stateToken, ContentStatusDTO status,
            AsyncCallback<StateAbstractDTO> asyncCallback);

    void setStatusAsAdmin(String userHash, StateToken stateToken, ContentStatusDTO status,
            AsyncCallback<StateAbstractDTO> asyncCallback);

    void setTags(String userHash, StateToken token, String tags, AsyncCallback<TagCloudResultDTO> asyncCallback);

    void voteComment(String userHash, StateToken token, Long commentId, boolean votePositive,
            AsyncCallback<CommentDTO> asyncCallback);
}
