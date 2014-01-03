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

@RemoteServiceRelativePath("ContentService")
public interface ContentService extends RemoteService {

  void addAuthor(String userHash, StateToken token, String authorShortName) throws DefaultException;

  StateContentDTO addContent(String user, StateToken parentToken, String name, String typeId)
      throws DefaultException;

  StateContainerDTO addFolder(String hash, StateToken parentToken, String typeId, String title)
      throws DefaultException;

  void addGadgetToContent(String userHash, StateToken currentStateToken, String gadgetName)
      throws DefaultException;

  StateContentDTO addNewContentWithGadget(String userHash, StateToken currentStateToken,
      String gadgetName, String typeId, String title, String body) throws DefaultException;;

  StateContentDTO addNewContentWithGadgetAndState(String userHash, StateToken currentStateToken,
      String gadgetName, String typeId, String tile, String body, Map<String, String> gadgetState)
      throws DefaultException;;

  Boolean addParticipant(String userHash, StateToken token, String participant) throws DefaultException;

  Boolean addParticipants(String userHash, StateToken token, String groupName,
      SocialNetworkSubGroup subGroup) throws DefaultException;

  StateContainerDTO addRoom(String user, StateToken parentToken, String name) throws DefaultException;

  StateContentDTO copyContent(String userHash, StateToken parentToken, StateToken token)
      throws DefaultException;

  StateContainerDTO delContent(String userHash, StateToken token) throws DefaultException;

  Boolean delParticipants(String userHash, StateToken token, String[] participants);

  Boolean delPublicParticipant(String userHash, StateToken token) throws DefaultException;

  StateAbstractDTO getContent(String userHash, StateToken token) throws DefaultException;

  StateAbstractDTO getContentByWaveRef(String userHash, String waveRef) throws DefaultException;

  TagCloudResult getSummaryTags(String userHash, StateToken groupToken) throws DefaultException;

  StateContainerDTO moveContent(String userHash, StateToken contentToken, StateToken newContainerToken)
      throws DefaultException;

  StateContainerDTO purgeAll(String userHash, StateToken token);

  StateContainerDTO purgeContent(String userHash, StateToken token);

  RateResult rateContent(String userHash, StateToken token, Double value) throws DefaultException;

  void removeAuthor(String userHash, StateToken token, String authorShortName) throws DefaultException;

  // void save(String user, StateToken token, String content) throws
  // DefaultException;

  StateAbstractDTO renameContainer(String userHash, StateToken token, String newName)
      throws DefaultException;

  StateAbstractDTO renameContent(String userHash, StateToken token, String newName)
      throws DefaultException;

  String sendFeedback(String userHash, String title, String body);

  ContentSimpleDTO setAsDefaultContent(String userHash, StateToken token);

  void setGadgetProperties(final String userHash, final StateToken currentStateToken,
      final String gadgetName, final Map<String, String> properties) throws DefaultException;

  I18nLanguageDTO setLanguage(String userHash, StateToken token, String languageCode)
      throws DefaultException;

  void setPublishedOn(String userHash, StateToken token, Date date) throws DefaultException;

  StateAbstractDTO setStatus(String userHash, StateToken stateToken, ContentStatus status);

  StateAbstractDTO setStatusAsAdmin(String userHash, StateToken stateToken, ContentStatus status);

  TagCloudResult setTags(String userHash, StateToken token, String tags) throws DefaultException;

  StateContentDTO setVisible(String userHash, StateToken token, boolean visible);

  String writeTo(String userHash, StateToken token, boolean onlyToAdmins) throws DefaultException;

  String writeTo(String userHash, StateToken token, boolean onlyToAdmins, String title, String message)
      throws DefaultException;

  String writeToParticipants(String userHash, StateToken token) throws DefaultException;;
}
