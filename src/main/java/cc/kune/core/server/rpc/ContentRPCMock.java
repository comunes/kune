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
package cc.kune.core.server.rpc;

import java.util.Date;
import java.util.Map;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.rpcservices.ContentService;
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

// TODO: Auto-generated Javadoc
/**
 * The Class ContentRPCMock.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ContentRPCMock implements ContentService, RPC {

  /** The container mock. */
  private final StateContainerDTO containerMock;

  /** The content mock. */
  private final StateContentDTO contentMock;

  /**
   * Instantiates a new content rpc mock.
   */
  public ContentRPCMock() {
    containerMock = new StateContainerDTO();
    contentMock = new StateContentDTO();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#addAuthor(java.lang.String,
   * cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  public void addAuthor(final String userHash, final StateToken token, final String authorShortName)
      throws DefaultException {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#addContent(java.lang.String,
   * cc.kune.core.shared.domain.utils.StateToken, java.lang.String,
   * java.lang.String)
   */
  @Override
  public StateContentDTO addContent(final String user, final StateToken parentToken, final String name,
      final String typeId) throws DefaultException {
    return contentMock;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#addFolder(java.lang.String,
   * cc.kune.core.shared.domain.utils.StateToken, java.lang.String,
   * java.lang.String)
   */
  @Override
  public StateContainerDTO addFolder(final String hash, final StateToken parentToken,
      final String typeId, final String title) throws DefaultException {
    return containerMock;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#addGadgetToContent(java.
   * lang.String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  public void addGadgetToContent(final String userHash, final StateToken currentStateToken,
      final String gadgetName) throws DefaultException {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#addNewContentWithGadget(
   * java.lang.String, cc.kune.core.shared.domain.utils.StateToken,
   * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public StateContentDTO addNewContentWithGadget(final String userHash,
      final StateToken currentStateToken, final String gadgetName, final String typeId,
      final String title, final String body) throws DefaultException {
    return contentMock;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#addNewContentWithGadgetAndState
   * (java.lang.String, cc.kune.core.shared.domain.utils.StateToken,
   * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
   * java.util.Map)
   */
  @Override
  public StateContentDTO addNewContentWithGadgetAndState(final String userHash,
      final StateToken currentStateToken, final String gadgetName, final String typeId,
      final String tile, final String body, final Map<String, String> gadgetState)
      throws DefaultException {
    return contentMock;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#addParticipant(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  public Boolean addParticipant(final String userHash, final StateToken token, final String participant)
      throws DefaultException {
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#addParticipants(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String,
   * cc.kune.core.shared.dto.SocialNetworkSubGroup)
   */
  @Override
  public Boolean addParticipants(final String userHash, final StateToken token, final String groupName,
      final SocialNetworkSubGroup subGroup) throws DefaultException {
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#addRoom(java.lang.String,
   * cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  public StateContainerDTO addRoom(final String user, final StateToken parentToken, final String name)
      throws DefaultException {
    return containerMock;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#copyContent(java.lang.String
   * , cc.kune.core.shared.domain.utils.StateToken,
   * cc.kune.core.shared.domain.utils.StateToken)
   */
  @Override
  public StateContentDTO copyContent(final String userHash, final StateToken parentToken,
      final StateToken token) throws DefaultException {
    return contentMock;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#delContent(java.lang.String,
   * cc.kune.core.shared.domain.utils.StateToken)
   */
  @Override
  public StateContainerDTO delContent(final String userHash, final StateToken token)
      throws DefaultException {
    return containerMock;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#getContent(java.lang.String,
   * cc.kune.core.shared.domain.utils.StateToken)
   */
  @Override
  public Boolean delParticipants(final String userHash, final StateToken token,
      final String[] participants) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Boolean delPublicParticipant(final String userHash, final StateToken token)
      throws DefaultException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public StateAbstractDTO getContent(final String userHash, final StateToken token)
      throws DefaultException {
    return contentMock;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#getContentByWaveRef(java
   * .lang.String, java.lang.String)
   */
  @Override
  public StateContentDTO getContentByWaveRef(final String userHash, final String waveRef)
      throws DefaultException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#getSummaryTags(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken)
   */
  @Override
  public TagCloudResult getSummaryTags(final String userHash, final StateToken groupToken)
      throws DefaultException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#moveContent(java.lang.String
   * , cc.kune.core.shared.domain.utils.StateToken,
   * cc.kune.core.shared.domain.utils.StateToken)
   */
  @Override
  public StateContainerDTO moveContent(final String userHash, final StateToken contentToken,
      final StateToken newContainerToken) throws DefaultException {
    return containerMock;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#purgeAll(java.lang.String,
   * cc.kune.core.shared.domain.utils.StateToken)
   */
  @Override
  public StateContainerDTO purgeAll(final String userHash, final StateToken token) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#purgeContent(java.lang.String
   * , cc.kune.core.shared.domain.utils.StateToken)
   */
  @Override
  public StateContainerDTO purgeContent(final String userHash, final StateToken token) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#rateContent(java.lang.String
   * , cc.kune.core.shared.domain.utils.StateToken, java.lang.Double)
   */
  @Override
  public RateResult rateContent(final String userHash, final StateToken token, final Double value)
      throws DefaultException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#removeAuthor(java.lang.String
   * , cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  public void removeAuthor(final String userHash, final StateToken token, final String authorShortName)
      throws DefaultException {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#renameContainer(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  public StateAbstractDTO renameContainer(final String userHash, final StateToken token,
      final String newName) throws DefaultException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#renameContent(java.lang.
   * String, cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  public StateAbstractDTO renameContent(final String userHash, final StateToken token,
      final String newName) throws DefaultException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#sendFeedback(java.lang.String
   * , java.lang.String, java.lang.String)
   */
  @Override
  public String sendFeedback(final String userHash, final String title, final String body) {
    return "#fixme";
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#setAsDefaultContent(java
   * .lang.String, cc.kune.core.shared.domain.utils.StateToken)
   */
  @Override
  public ContentSimpleDTO setAsDefaultContent(final String userHash, final StateToken token) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#setGadgetProperties(java
   * .lang.String, cc.kune.core.shared.domain.utils.StateToken,
   * java.lang.String, java.util.Map)
   */
  @Override
  public void setGadgetProperties(final String userHash, final StateToken currentStateToken,
      final String gadgetName, final Map<String, String> properties) throws DefaultException {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#setLanguage(java.lang.String
   * , cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  public I18nLanguageDTO setLanguage(final String userHash, final StateToken token,
      final String languageCode) throws DefaultException {
    final I18nLanguageDTO lang = new I18nLanguageDTO();

    return lang;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#setPublishedOn(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken, java.util.Date)
   */
  @Override
  public void setPublishedOn(final String userHash, final StateToken token, final Date date)
      throws DefaultException {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#setStatus(java.lang.String,
   * cc.kune.core.shared.domain.utils.StateToken,
   * cc.kune.core.shared.domain.ContentStatus)
   */
  @Override
  public StateAbstractDTO setStatus(final String userHash, final StateToken stateToken,
      final ContentStatus status) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#setStatusAsAdmin(java.lang
   * .String, cc.kune.core.shared.domain.utils.StateToken,
   * cc.kune.core.shared.domain.ContentStatus)
   */
  @Override
  public StateAbstractDTO setStatusAsAdmin(final String userHash, final StateToken stateToken,
      final ContentStatus status) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#setTags(java.lang.String,
   * cc.kune.core.shared.domain.utils.StateToken, java.lang.String)
   */
  @Override
  public TagCloudResult setTags(final String userHash, final StateToken token, final String tags)
      throws DefaultException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#writeTo(java.lang.String,
   * cc.kune.core.shared.domain.utils.StateToken, boolean)
   */
  @Override
  public StateContentDTO setVisible(final String userHash, final StateToken token, final boolean visible) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String writeTo(final String userHash, final StateToken token, final boolean onlyToAdmins)
      throws DefaultException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#writeTo(java.lang.String,
   * cc.kune.core.shared.domain.utils.StateToken, boolean, java.lang.String,
   * java.lang.String)
   */
  @Override
  public String writeTo(final String userHash, final StateToken token, final boolean onlyToAdmins,
      final String title, final String message) throws DefaultException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.rpcservices.ContentService#writeToParticipants(java
   * .lang.String, cc.kune.core.shared.domain.utils.StateToken)
   */
  @Override
  public String writeToParticipants(final String userHash, final StateToken token)
      throws DefaultException {
    // TODO Auto-generated method stub
    return null;
  }

}
