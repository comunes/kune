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

import cc.kune.core.client.errors.DefaultException;
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

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// TODO: Auto-generated Javadoc
/**
 * The Interface ContentService.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@RemoteServiceRelativePath("ContentService")
public interface ContentService extends RemoteService {

  /**
   * Adds the author.
   *
   * @param userHash the user hash
   * @param token the token
   * @param authorShortName the author short name
   * @throws DefaultException the default exception
   */
  void addAuthor(String userHash, StateToken token, String authorShortName) throws DefaultException;

  /**
   * Adds the content.
   *
   * @param user the user
   * @param parentToken the parent token
   * @param name the name
   * @param typeId the type id
   * @return the state content dto
   * @throws DefaultException the default exception
   */
  StateContentDTO addContent(String user, StateToken parentToken, String name, String typeId)
      throws DefaultException;

  /**
   * Adds the folder.
   *
   * @param hash the hash
   * @param parentToken the parent token
   * @param typeId the type id
   * @param title the title
   * @return the state container dto
   * @throws DefaultException the default exception
   */
  StateContainerDTO addFolder(String hash, StateToken parentToken, String typeId, String title)
      throws DefaultException;

  /**
   * Adds the gadget to content.
   *
   * @param userHash the user hash
   * @param currentStateToken the current state token
   * @param gadgetName the gadget name
   * @throws DefaultException the default exception
   */
  void addGadgetToContent(String userHash, StateToken currentStateToken, String gadgetName)
      throws DefaultException;

  /**
   * Adds the new content with gadget.
   *
   * @param userHash the user hash
   * @param currentStateToken the current state token
   * @param gadgetName the gadget name
   * @param typeId the type id
   * @param title the title
   * @param body the body
   * @return the state content dto
   * @throws DefaultException the default exception
   */
  StateContentDTO addNewContentWithGadget(String userHash, StateToken currentStateToken,
      String gadgetName, String typeId, String title, String body) throws DefaultException;;

  /**
   * Adds the new content with gadget and state.
   *
   * @param userHash the user hash
   * @param currentStateToken the current state token
   * @param gadgetName the gadget name
   * @param typeId the type id
   * @param tile the tile
   * @param body the body
   * @param gadgetState the gadget state
   * @return the state content dto
   * @throws DefaultException the default exception
   */
  StateContentDTO addNewContentWithGadgetAndState(String userHash, StateToken currentStateToken,
      String gadgetName, String typeId, String tile, String body, Map<String, String> gadgetState)
      throws DefaultException;;

  /**
   * Adds the participant.
   *
   * @param userHash the user hash
   * @param token the token
   * @param participant the participant
   * @return the boolean
   * @throws DefaultException the default exception
   */
  Boolean addParticipant(String userHash, StateToken token, String participant) throws DefaultException;

  /**
   * Adds the participants.
   *
   * @param userHash the user hash
   * @param token the token
   * @param groupName the group name
   * @param subGroup the sub group
   * @return the boolean
   * @throws DefaultException the default exception
   */
  Boolean addParticipants(String userHash, StateToken token, String groupName,
      SocialNetworkSubGroup subGroup) throws DefaultException;

  /**
   * Adds the room.
   *
   * @param user the user
   * @param parentToken the parent token
   * @param name the name
   * @return the state container dto
   * @throws DefaultException the default exception
   */
  StateContainerDTO addRoom(String user, StateToken parentToken, String name) throws DefaultException;

  /**
   * Copy content.
   *
   * @param userHash the user hash
   * @param parentToken the parent token
   * @param token the token
   * @return the state content dto
   * @throws DefaultException the default exception
   */
  StateContentDTO copyContent(String userHash, StateToken parentToken, StateToken token)
      throws DefaultException;

  /**
   * Del content.
   *
   * @param userHash the user hash
   * @param token the token
   * @return the state container dto
   * @throws DefaultException the default exception
   */
  StateContainerDTO delContent(String userHash, StateToken token) throws DefaultException;

  /**
   * Gets the content.
   *
   * @param userHash the user hash
   * @param token the token
   * @return the content
   * @throws DefaultException the default exception
   */
  StateAbstractDTO getContent(String userHash, StateToken token) throws DefaultException;

  /**
   * Gets the content by wave ref.
   *
   * @param userHash the user hash
   * @param waveRef the wave ref
   * @return the content by wave ref
   * @throws DefaultException the default exception
   */
  StateAbstractDTO getContentByWaveRef(String userHash, String waveRef) throws DefaultException;

  /**
   * Gets the summary tags.
   *
   * @param userHash the user hash
   * @param groupToken the group token
   * @return the summary tags
   * @throws DefaultException the default exception
   */
  TagCloudResult getSummaryTags(String userHash, StateToken groupToken) throws DefaultException;

  /**
   * Move content.
   *
   * @param userHash the user hash
   * @param contentToken the content token
   * @param newContainerToken the new container token
   * @return the state container dto
   * @throws DefaultException the default exception
   */
  StateContainerDTO moveContent(String userHash, StateToken contentToken, StateToken newContainerToken)
      throws DefaultException;

  /**
   * Purge all.
   *
   * @param userHash the user hash
   * @param token the token
   * @return the state container dto
   */
  StateContainerDTO purgeAll(String userHash, StateToken token);

  /**
   * Purge content.
   *
   * @param userHash the user hash
   * @param token the token
   * @return the state container dto
   */
  StateContainerDTO purgeContent(String userHash, StateToken token);

  /**
   * Rate content.
   *
   * @param userHash the user hash
   * @param token the token
   * @param value the value
   * @return the rate result
   * @throws DefaultException the default exception
   */
  RateResult rateContent(String userHash, StateToken token, Double value) throws DefaultException;

  /**
   * Removes the author.
   *
   * @param userHash the user hash
   * @param token the token
   * @param authorShortName the author short name
   * @throws DefaultException the default exception
   */
  void removeAuthor(String userHash, StateToken token, String authorShortName) throws DefaultException;

  /**
   * Rename container.
   *
   * @param userHash the user hash
   * @param token the token
   * @param newName the new name
   * @return the state abstract dto
   * @throws DefaultException the default exception
   */
  StateAbstractDTO renameContainer(String userHash, StateToken token, String newName)
      throws DefaultException;

  // void save(String user, StateToken token, String content) throws
  // DefaultException;

  /**
   * Rename content.
   *
   * @param userHash the user hash
   * @param token the token
   * @param newName the new name
   * @return the state abstract dto
   * @throws DefaultException the default exception
   */
  StateAbstractDTO renameContent(String userHash, StateToken token, String newName)
      throws DefaultException;

  /**
   * Send feedback.
   *
   * @param userHash the user hash
   * @param title the title
   * @param body the body
   * @return the string
   */
  String sendFeedback(String userHash, String title, String body);

  /**
   * Sets the as default content.
   *
   * @param userHash the user hash
   * @param token the token
   * @return the content simple dto
   */
  ContentSimpleDTO setAsDefaultContent(String userHash, StateToken token);

  /**
   * Sets the gadget properties.
   *
   * @param userHash the user hash
   * @param currentStateToken the current state token
   * @param gadgetName the gadget name
   * @param properties the properties
   * @throws DefaultException the default exception
   */
  void setGadgetProperties(final String userHash, final StateToken currentStateToken,
      final String gadgetName, final Map<String, String> properties) throws DefaultException;

  /**
   * Sets the language.
   *
   * @param userHash the user hash
   * @param token the token
   * @param languageCode the language code
   * @return the i18n language dto
   * @throws DefaultException the default exception
   */
  I18nLanguageDTO setLanguage(String userHash, StateToken token, String languageCode)
      throws DefaultException;

  /**
   * Sets the published on.
   *
   * @param userHash the user hash
   * @param token the token
   * @param date the date
   * @throws DefaultException the default exception
   */
  void setPublishedOn(String userHash, StateToken token, Date date) throws DefaultException;

  /**
   * Sets the status.
   *
   * @param userHash the user hash
   * @param stateToken the state token
   * @param status the status
   * @return the state abstract dto
   */
  StateAbstractDTO setStatus(String userHash, StateToken stateToken, ContentStatus status);

  /**
   * Sets the status as admin.
   *
   * @param userHash the user hash
   * @param stateToken the state token
   * @param status the status
   * @return the state abstract dto
   */
  StateAbstractDTO setStatusAsAdmin(String userHash, StateToken stateToken, ContentStatus status);

  /**
   * Sets the tags.
   *
   * @param userHash the user hash
   * @param token the token
   * @param tags the tags
   * @return the tag cloud result
   * @throws DefaultException the default exception
   */
  TagCloudResult setTags(String userHash, StateToken token, String tags) throws DefaultException;

  /**
   * Write to.
   *
   * @param userHash the user hash
   * @param token the token
   * @param onlyToAdmins the only to admins
   * @return the string
   * @throws DefaultException the default exception
   */
  String writeTo(String userHash, StateToken token, boolean onlyToAdmins) throws DefaultException;

  /**
   * Write to.
   *
   * @param userHash the user hash
   * @param token the token
   * @param onlyToAdmins the only to admins
   * @param title the title
   * @param message the message
   * @return the string
   * @throws DefaultException the default exception
   */
  String writeTo(String userHash, StateToken token, boolean onlyToAdmins, String title, String message)
      throws DefaultException;

  /**
   * Write to participants.
   *
   * @param userHash the user hash
   * @param token the token
   * @return the string
   * @throws DefaultException the default exception
   */
  String writeToParticipants(String userHash, StateToken token) throws DefaultException;;
}
