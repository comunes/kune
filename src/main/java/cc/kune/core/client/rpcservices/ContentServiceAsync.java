/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
import java.util.Map;

import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.RateResult;
import cc.kune.core.shared.domain.TagCloudResult;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;
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

  void addGadgetToContent(String userHash, StateToken currentStateToken, String gadgetName,
      AsyncCallback<Void> asyncCallback);

  void addNewContentWithGadget(String userHash, StateToken currentStateToken, String gadgetName,
      String typeId, String title, String body, AsyncCallback<StateContentDTO> asyncCallback);

  void addNewContentWithGadgetAndState(String userHash, StateToken currentStateToken, String gadgetName,
      String typeId, String title, String body, Map<String, String> gadgetState,
      AsyncCallback<StateContentDTO> callback);

  void addParticipant(String userHash, StateToken token, String participant,
      AsyncCallback<Boolean> asyncCallback);

  void addParticipants(String userHash, StateToken token, String groupName,
      SocialNetworkSubGroup subGroup, AsyncCallback<Boolean> callback);

  void addRoom(String user, StateToken parentToken, String name,
      AsyncCallback<StateContainerDTO> callback);

  void copyContent(String userHash, StateToken parentToken, StateToken token,
      AsyncCallback<StateContentDTO> callback);

  void delContent(String userHash, StateToken token, AsyncCallback<StateContainerDTO> asyncCallback);

  void delParticipants(String userHash, StateToken token, String[] participants,
      AsyncCallback<Boolean> callback);

  void delPublicParticipant(String userHash, StateToken token, AsyncCallback<Boolean> callback);

  void getContent(String user, StateToken newState, AsyncCallback<StateAbstractDTO> callback);

  void getContentByWaveRef(String userHash, String waveRef, AsyncCallback<StateAbstractDTO> callback);

  void getSummaryTags(String userHash, StateToken groupToken, AsyncCallback<TagCloudResult> asyncCallback);

  void moveContent(String userHash, StateToken contentToken, StateToken newContainerToken,
      AsyncCallback<StateContainerDTO> asyncCallback);

  void purgeAll(String userHash, StateToken token, AsyncCallback<StateContainerDTO> callback);

  void purgeContent(String userHash, StateToken token, AsyncCallback<StateContainerDTO> callback);

  void rateContent(String userHash, StateToken token, Double value,
      AsyncCallback<RateResult> asyncCallback);

  // void save(String user, StateToken token, String content,
  // AsyncCallback<Void> asyncCallback);

  void removeAuthor(String userHash, StateToken token, String authorShortName,
      AsyncCallback<Void> asyncCallback);

  void renameContainer(String userHash, StateToken token, String newName,
      AsyncCallback<StateAbstractDTO> asyncCallback);

  void renameContent(String userHash, StateToken token, String newName,
      AsyncCallback<StateAbstractDTO> asyncCallback);

  void sendFeedback(String userHash, String title, String body, AsyncCallback<String> callback);

  void setAsDefaultContent(String userHash, StateToken token,
      AsyncCallback<ContentSimpleDTO> asyncCallback);

  void setGadgetProperties(String userHash, StateToken currentStateToken, String gadgetName,
      Map<String, String> properties, AsyncCallback<Void> callback);

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

  void setVisible(String userHash, StateToken token, boolean visible,
      AsyncCallback<StateContentDTO> callback);

  void writeTo(String userHash, StateToken token, boolean onlyToAdmins, AsyncCallback<String> callback);

  void writeTo(String userHash, StateToken token, boolean onlyToAdmins, String title, String message,
      AsyncCallback<String> callback);

  void writeToParticipants(String userHash, StateToken token, AsyncCallback<String> callback);

}
