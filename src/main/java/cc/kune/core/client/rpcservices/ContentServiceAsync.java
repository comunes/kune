/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

// TODO: Auto-generated Javadoc
/**
 * The Interface ContentServiceAsync.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface ContentServiceAsync {

  /**
   * Adds the author.
   *
   * @param userHash the user hash
   * @param token the token
   * @param authorShortName the author short name
   * @param asyncCallback the async callback
   */
  void addAuthor(String userHash, StateToken token, String authorShortName,
      AsyncCallback<Void> asyncCallback);

  /**
   * Adds the content.
   *
   * @param user the user
   * @param parentToken the parent token
   * @param name the name
   * @param typeId the type id
   * @param callback the callback
   */
  void addContent(String user, StateToken parentToken, String name, String typeId,
      AsyncCallback<StateContentDTO> callback);

  /**
   * Adds the folder.
   *
   * @param hash the hash
   * @param parentToken the parent token
   * @param title the title
   * @param typeId the type id
   * @param callback the callback
   */
  void addFolder(String hash, StateToken parentToken, String title, String typeId,
      AsyncCallback<StateContainerDTO> callback);

  /**
   * Adds the gadget to content.
   *
   * @param userHash the user hash
   * @param currentStateToken the current state token
   * @param gadgetName the gadget name
   * @param asyncCallback the async callback
   */
  void addGadgetToContent(String userHash, StateToken currentStateToken, String gadgetName,
      AsyncCallback<Void> asyncCallback);

  /**
   * Adds the new content with gadget.
   *
   * @param userHash the user hash
   * @param currentStateToken the current state token
   * @param gadgetName the gadget name
   * @param typeId the type id
   * @param title the title
   * @param body the body
   * @param asyncCallback the async callback
   */
  void addNewContentWithGadget(String userHash, StateToken currentStateToken, String gadgetName,
      String typeId, String title, String body, AsyncCallback<StateContentDTO> asyncCallback);

  /**
   * Adds the new content with gadget and state.
   *
   * @param userHash the user hash
   * @param currentStateToken the current state token
   * @param gadgetName the gadget name
   * @param typeId the type id
   * @param title the title
   * @param body the body
   * @param gadgetState the gadget state
   * @param callback the callback
   */
  void addNewContentWithGadgetAndState(String userHash, StateToken currentStateToken, String gadgetName,
      String typeId, String title, String body, Map<String, String> gadgetState,
      AsyncCallback<StateContentDTO> callback);

  /**
   * Adds the participant.
   *
   * @param userHash the user hash
   * @param token the token
   * @param participant the participant
   * @param asyncCallback the async callback
   */
  void addParticipant(String userHash, StateToken token, String participant,
      AsyncCallback<Boolean> asyncCallback);

  /**
   * Adds the participants.
   *
   * @param userHash the user hash
   * @param token the token
   * @param groupName the group name
   * @param subGroup the sub group
   * @param callback the callback
   */
  void addParticipants(String userHash, StateToken token, String groupName,
      SocialNetworkSubGroup subGroup, AsyncCallback<Boolean> callback);

  /**
   * Adds the room.
   *
   * @param user the user
   * @param parentToken the parent token
   * @param name the name
   * @param callback the callback
   */
  void addRoom(String user, StateToken parentToken, String name,
      AsyncCallback<StateContainerDTO> callback);

  /**
   * Copy content.
   *
   * @param userHash the user hash
   * @param parentToken the parent token
   * @param token the token
   * @param callback the callback
   */
  void copyContent(String userHash, StateToken parentToken, StateToken token,
      AsyncCallback<StateContentDTO> callback);

  /**
   * Del content.
   *
   * @param userHash the user hash
   * @param token the token
   * @param asyncCallback the async callback
   */
  void delContent(String userHash, StateToken token, AsyncCallback<StateContainerDTO> asyncCallback);

  /**
   * Gets the content.
   *
   * @param user the user
   * @param newState the new state
   * @param callback the callback
   * @return the content
   */
  void getContent(String user, StateToken newState, AsyncCallback<StateAbstractDTO> callback);

  /**
   * Gets the content by wave ref.
   *
   * @param userHash the user hash
   * @param waveRef the wave ref
   * @param callback the callback
   * @return the content by wave ref
   */
  void getContentByWaveRef(String userHash, String waveRef, AsyncCallback<StateAbstractDTO> callback);

  /**
   * Gets the summary tags.
   *
   * @param userHash the user hash
   * @param groupToken the group token
   * @param asyncCallback the async callback
   * @return the summary tags
   */
  void getSummaryTags(String userHash, StateToken groupToken, AsyncCallback<TagCloudResult> asyncCallback);

  /**
   * Move content.
   *
   * @param userHash the user hash
   * @param contentToken the content token
   * @param newContainerToken the new container token
   * @param asyncCallback the async callback
   */
  void moveContent(String userHash, StateToken contentToken, StateToken newContainerToken,
      AsyncCallback<StateContainerDTO> asyncCallback);

  /**
   * Purge all.
   *
   * @param userHash the user hash
   * @param token the token
   * @param callback the callback
   */
  void purgeAll(String userHash, StateToken token, AsyncCallback<StateContainerDTO> callback);

  /**
   * Purge content.
   *
   * @param userHash the user hash
   * @param token the token
   * @param callback the callback
   */
  void purgeContent(String userHash, StateToken token, AsyncCallback<StateContainerDTO> callback);

  /**
   * Rate content.
   *
   * @param userHash the user hash
   * @param token the token
   * @param value the value
   * @param asyncCallback the async callback
   */
  void rateContent(String userHash, StateToken token, Double value,
      AsyncCallback<RateResult> asyncCallback);

  /**
   * Removes the author.
   *
   * @param userHash the user hash
   * @param token the token
   * @param authorShortName the author short name
   * @param asyncCallback the async callback
   */
  void removeAuthor(String userHash, StateToken token, String authorShortName,
      AsyncCallback<Void> asyncCallback);

  /**
   * Rename container.
   *
   * @param userHash the user hash
   * @param token the token
   * @param newName the new name
   * @param asyncCallback the async callback
   */
  void renameContainer(String userHash, StateToken token, String newName,
      AsyncCallback<StateAbstractDTO> asyncCallback);

  // void save(String user, StateToken token, String content,
  // AsyncCallback<Void> asyncCallback);

  /**
   * Rename content.
   *
   * @param userHash the user hash
   * @param token the token
   * @param newName the new name
   * @param asyncCallback the async callback
   */
  void renameContent(String userHash, StateToken token, String newName,
      AsyncCallback<StateAbstractDTO> asyncCallback);

  /**
   * Send feedback.
   *
   * @param userHash the user hash
   * @param title the title
   * @param body the body
   * @param callback the callback
   */
  void sendFeedback(String userHash, String title, String body, AsyncCallback<String> callback);

  /**
   * Sets the as default content.
   *
   * @param userHash the user hash
   * @param token the token
   * @param asyncCallback the async callback
   */
  void setAsDefaultContent(String userHash, StateToken token,
      AsyncCallback<ContentSimpleDTO> asyncCallback);

  /**
   * Sets the gadget properties.
   *
   * @param userHash the user hash
   * @param currentStateToken the current state token
   * @param gadgetName the gadget name
   * @param properties the properties
   * @param callback the callback
   */
  void setGadgetProperties(String userHash, StateToken currentStateToken, String gadgetName,
      Map<String, String> properties, AsyncCallback<Void> callback);

  /**
   * Sets the language.
   *
   * @param userHash the user hash
   * @param token the token
   * @param languageCode the language code
   * @param asyncCallback the async callback
   */
  void setLanguage(String userHash, StateToken token, String languageCode,
      AsyncCallback<I18nLanguageDTO> asyncCallback);

  /**
   * Sets the published on.
   *
   * @param userHash the user hash
   * @param token the token
   * @param publishedOn the published on
   * @param asyncCallback the async callback
   */
  void setPublishedOn(String userHash, StateToken token, Date publishedOn,
      AsyncCallback<Void> asyncCallback);

  /**
   * Sets the status.
   *
   * @param userHash the user hash
   * @param stateToken the state token
   * @param status the status
   * @param asyncCallback the async callback
   */
  void setStatus(String userHash, StateToken stateToken, ContentStatus status,
      AsyncCallback<StateAbstractDTO> asyncCallback);

  /**
   * Sets the status as admin.
   *
   * @param userHash the user hash
   * @param stateToken the state token
   * @param status the status
   * @param asyncCallback the async callback
   */
  void setStatusAsAdmin(String userHash, StateToken stateToken, ContentStatus status,
      AsyncCallback<StateAbstractDTO> asyncCallback);

  /**
   * Sets the tags.
   *
   * @param userHash the user hash
   * @param token the token
   * @param tags the tags
   * @param asyncCallback the async callback
   */
  void setTags(String userHash, StateToken token, String tags,
      AsyncCallback<TagCloudResult> asyncCallback);

  /**
   * Write to.
   *
   * @param userHash the user hash
   * @param token the token
   * @param onlyToAdmins the only to admins
   * @param callback the callback
   */
  void writeTo(String userHash, StateToken token, boolean onlyToAdmins, AsyncCallback<String> callback);

  /**
   * Write to.
   *
   * @param userHash the user hash
   * @param token the token
   * @param onlyToAdmins the only to admins
   * @param title the title
   * @param message the message
   * @param callback the callback
   */
  void writeTo(String userHash, StateToken token, boolean onlyToAdmins, String title, String message,
      AsyncCallback<String> callback);

  /**
   * Write to participants.
   *
   * @param userHash the user hash
   * @param token the token
   * @param callback the callback
   */
  void writeToParticipants(String userHash, StateToken token, AsyncCallback<String> callback);

}
