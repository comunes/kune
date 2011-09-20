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
package cc.kune.core.client.rpcservices;

import java.util.Date;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.RateResult;
import cc.kune.core.shared.domain.TagCloudResult;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContentServiceAsync {

  void addAuthor(String userHash, StateToken token, String authorShortName,
      AsyncCallback<Void> asyncCallback);

  void addContent(String user, StateToken parentToken, String name, String typeId,
      AsyncCallback<StateContentDTO> callback);

  void addFolder(String hash, StateToken parentToken, String title, String typeId,
      AsyncCallback<StateContainerDTO> callback);

  void addGadget(String userHash, StateToken currentStateToken, String gadgetName, String typeId,
      String title, String body, AsyncCallback<StateContentDTO> asyncCallback);

  void addParticipant(String userHash, StateToken token, String authorShortName,
      AsyncCallback<Void> asyncCallback) throws DefaultException;

  void addRoom(String user, StateToken parentToken, String name,
      AsyncCallback<StateContainerDTO> callback);

  void delContent(String userHash, StateToken token, AsyncCallback<StateContentDTO> asyncCallback);

  void getContent(String user, StateToken newState, AsyncCallback<StateAbstractDTO> callback);

  void getSummaryTags(String userHash, StateToken groupToken, AsyncCallback<TagCloudResult> asyncCallback);

  void moveContent(String userHash, StateToken contentToken, StateToken newContainerToken,
      AsyncCallback<StateContainerDTO> asyncCallback);

  void rateContent(String userHash, StateToken token, Double value,
      AsyncCallback<RateResult> asyncCallback);

  void removeAuthor(String userHash, StateToken token, String authorShortName,
      AsyncCallback<Void> asyncCallback);

  void renameContainer(String userHash, StateToken token, String newName,
      AsyncCallback<StateAbstractDTO> asyncCallback);

  void renameContent(String userHash, StateToken token, String newName,
      AsyncCallback<StateAbstractDTO> asyncCallback);

  void save(String user, StateToken token, String content, AsyncCallback<Void> asyncCallback);

  void sendFeedback(String userHash, String title, String body, AsyncCallback<String> callback);

  void setAsDefaultContent(String userHash, StateToken token,
      AsyncCallback<ContentSimpleDTO> asyncCallback);

  void setLanguage(String userHash, StateToken token, String languageCode,
      AsyncCallback<I18nLanguageDTO> asyncCallback);

  void setPublishedOn(String userHash, StateToken token, Date publishedOn,
      AsyncCallback<Void> asyncCallback);

  void setStatus(String userHash, StateToken stateToken, ContentStatus status,
      AsyncCallback<StateAbstractDTO> asyncCallback);

  void setStatusAsAdmin(String userHash, StateToken stateToken, ContentStatus status,
      AsyncCallback<StateAbstractDTO> asyncCallback);

  void setTags(String userHash, StateToken token, String tags,
      AsyncCallback<TagCloudResult> asyncCallback);

}
