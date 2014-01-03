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
package cc.kune.core.server.content;

import java.util.Date;
import java.util.Map;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.manager.Manager;
import cc.kune.core.server.manager.SearchResult;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.core.shared.domain.RateResult;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Interface ContentManager.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface ContentManager extends Manager<Content, Long> {

  /** The def global search fields with mime. */
  String[] DEF_GLOBAL_SEARCH_FIELDS_WITH_MIME = new String[] { "authors.name", "authors.shortName",
      "container.name", "language.code", "language.englishName", "language.nativeName",
      "lastRevision.body", "lastRevision.title", "mimeType.mimetype" };

  /** The def group search fields with mime. */
  String[] DEF_GROUP_SEARCH_FIELDS_WITH_MIME = new String[] { "lastRevision.title",
      "container.owner_shortName", "mimeType.mimetype" };

  /**
   * Adds the author.
   * 
   * @param user
   *          the user
   * @param contentId
   *          the content id
   * @param authorShortName
   *          the author short name
   * @throws DefaultException
   *           the default exception
   */
  void addAuthor(User user, Long contentId, String authorShortName) throws DefaultException;

  /**
   * Adds the gadget to content.
   * 
   * @param user
   *          the user
   * @param content
   *          the content
   * @param gadgetName
   *          the gadget name
   */
  void addGadgetToContent(User user, Content content, String gadgetName);

  /**
   * Adds the participant.
   * 
   * @param user
   *          the user
   * @param contentId
   *          the content id
   * @param participant
   *          the participant
   * @return true, if successful
   */
  boolean addParticipant(User user, Long contentId, String participant);

  /**
   * Adds the participants.
   * 
   * @param user
   *          the user
   * @param contentId
   *          the content id
   * @param group
   *          the group
   * @param whichOnes
   *          the which ones
   * @return true, if successful
   */
  boolean addParticipants(User user, Long contentId, Group group, SocialNetworkSubGroup whichOnes);

  Content addToAcl(Content content, Group group, AccessRol rol);

  Content copyContent(User user, Container container, Content contentToCopy);

  /**
   * Creates the gadget.
   * 
   * @param user
   *          the user
   * @param container
   *          the container
   * @param gadgetname
   *          the gadgetname
   * @param typeIdChild
   *          the type id child
   * @param title
   *          the title
   * @param body
   *          the body
   * @param gadgetProperties
   *          the gadget properties
   * @return the content
   */
  Content createGadget(User user, Container container, String gadgetname, String typeIdChild,
      String title, String body, Map<String, String> gadgetProperties);

  boolean delParticipants(User user, Long contentId, String... participants);

  boolean delPublicParticipant(User user, Long contentId);

  boolean findIfExistsTitle(Container container, String title);

  /**
   * Gets the rate avg.
   * 
   * @param content
   *          the content
   * @return the rate avg
   */
  Double getRateAvg(Content content);

  /**
   * Gets the rate by users.
   * 
   * @param content
   *          the content
   * @return the rate by users
   */
  Long getRateByUsers(Content content);

  /**
   * Gets the rate content.
   * 
   * @param user
   *          the user
   * @param content
   *          the content
   * @return the rate content
   */
  Double getRateContent(User user, Content content);

  /**
   * Move content.
   * 
   * @param content
   *          the content
   * @param newContainer
   *          the new container
   * @return the content
   */
  Content moveContent(Content content, Container newContainer);

  /**
   * Purge all.
   * 
   * @param container
   *          the container
   * @return the container
   */
  Container purgeAll(Container container);

  /**
   * Purge content.
   * 
   * @param content
   *          the content
   * @return the container
   */
  Container purgeContent(Content content);

  /**
   * Rate content.
   * 
   * @param rater
   *          the rater
   * @param contentId
   *          the content id
   * @param value
   *          the value
   * @return the rate result
   * @throws DefaultException
   *           the default exception
   */
  RateResult rateContent(User rater, Long contentId, Double value) throws DefaultException;

  /**
   * Removes the author.
   * 
   * @param user
   *          the user
   * @param contentId
   *          the content id
   * @param authorShortName
   *          the author short name
   * @throws DefaultException
   *           the default exception
   */
  void removeAuthor(User user, Long contentId, String authorShortName) throws DefaultException;

  Content removeFromAcl(Content content, Group group, AccessRol rol);

  Content renameContent(User user, Long contentId, String newName) throws DefaultException;

  /**
   * Save.
   * 
   * @param content
   *          the content
   * @return the content
   */
  Content save(Content content);

  /**
   * Save.
   * 
   * @param editor
   *          the editor
   * @param content
   *          the content
   * @param body
   *          the body
   * @return the content
   */
  Content save(User editor, Content content, String body);

  /**
   * Search.
   * 
   * @param search
   *          the search
   * @return the search result
   */
  SearchResult<Content> search(String search);

  /**
   * Search.
   * 
   * @param search
   *          the search
   * @param firstResult
   *          the first result
   * @param maxResults
   *          the max results
   * @return the search result
   */
  SearchResult<Content> search(String search, Integer firstResult, Integer maxResults);

  /**
   * Search mime.
   * 
   * @param search
   *          the search
   * @param firstResult
   *          the first result
   * @param maxResults
   *          the max results
   * @param group
   *          the group
   * @param mimetype
   *          the mimetype
   * @return the search result
   */
  SearchResult<Content> searchMime(String search, Integer firstResult, Integer maxResults, String group,
      String mimetype);

  /**
   * Search mime.
   * 
   * @param search
   *          the search
   * @param firstResult
   *          the first result
   * @param maxResults
   *          the max results
   * @param group
   *          the group
   * @param mimetype
   *          the mimetype
   * @param mimetype2
   *          the mimetype2
   * @return the search result
   */
  SearchResult<?> searchMime(String search, Integer firstResult, Integer maxResults, String group,
      String mimetype, String mimetype2);

  Content setAclMode(Content content, AccessRol rol, GroupListMode mode);

  void setGadgetProperties(User user, Content content, String gadgetName, Map<String, String> properties);

  /**
   * Sets the language.
   * 
   * @param user
   *          the user
   * @param contentId
   *          the content id
   * @param languageCode
   *          the language code
   * @return the i18n language
   * @throws DefaultException
   *           the default exception
   */
  I18nLanguage setLanguage(User user, Long contentId, String languageCode) throws DefaultException;

  /**
   * Sets the modified on.
   * 
   * @param content
   *          the content
   * @param lastModifiedTime
   *          the last modified time
   */
  void setModifiedOn(Content content, long lastModifiedTime);

  /**
   * Sets the published on.
   * 
   * @param user
   *          the user
   * @param contentId
   *          the content id
   * @param publishedOn
   *          the published on
   * @throws DefaultException
   *           the default exception
   */
  void setPublishedOn(User user, Long contentId, Date publishedOn) throws DefaultException;

  /**
   * Sets the status.
   * 
   * @param contentId
   *          the content id
   * @param contentStatus
   *          the content status
   * @return the content
   */
  Content setStatus(Long contentId, ContentStatus contentStatus);

  /**
   * Sets the tags.
   * 
   * @param user
   *          the user
   * @param contentId
   *          the content id
   * @param tags
   *          the tags
   * @throws DefaultException
   *           the default exception
   */
  void setTags(User user, Long contentId, String tags) throws DefaultException;

  Content setVisible(Content content, boolean visible);

}
