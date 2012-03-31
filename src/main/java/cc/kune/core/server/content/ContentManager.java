/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.content;

import java.util.Date;
import java.util.Map;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.manager.Manager;
import cc.kune.core.server.manager.SearchResult;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.RateResult;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.User;

public interface ContentManager extends Manager<Content, Long> {

  String[] DEF_GLOBAL_SEARCH_FIELDS = new String[] { "authors.name", "authors.shortName",
      "container.name", "language.code", "language.englishName", "language.nativeName",
      "lastRevision.body", "lastRevision.title" };

  String[] DEF_GLOBAL_SEARCH_FIELDS_WITH_MIME = new String[] { "authors.name", "authors.shortName",
      "container.name", "language.code", "language.englishName", "language.nativeName",
      "lastRevision.body", "lastRevision.title", "mimeType.mimetype" };

  String[] DEF_GROUP_SEARCH_FIELDS_WITH_MIME = new String[] { "lastRevision.title",
      "container.owner_shortName", "mimeType.mimetype" };

  void addAuthor(User user, Long contentId, String authorShortName) throws DefaultException;

  void addGadgetToContent(User user, Content content, String gadgetName);

  boolean addParticipant(User user, Long contentId, String participant);

  boolean addParticipants(User user, Long contentId, Group group, SocialNetworkSubGroup whichOnes);

  Content copyContent(User user, Container container, Content contentToCopy);

  Content createGadget(User user, Container container, String gadgetname, String typeIdChild,
      String title, String body, Map<String, String> gadgetProperties);

  boolean findIfExistsTitle(Container container, String title);

  Double getRateAvg(Content content);

  Long getRateByUsers(Content content);

  Double getRateContent(User user, Content content);

  Content moveContent(Content content, Container newContainer);

  Container purgeContent(Content content);

  RateResult rateContent(User rater, Long contentId, Double value) throws DefaultException;

  void removeAuthor(User user, Long contentId, String authorShortName) throws DefaultException;

  Content renameContent(User user, Long contentId, String newName) throws DefaultException;

  Content save(Content content);

  Content save(User editor, Content content, String body);

  SearchResult<Content> search(String search);

  SearchResult<Content> search(String search, Integer firstResult, Integer maxResults);

  SearchResult<Content> searchMime(String search, Integer firstResult, Integer maxResults, String group,
      String mimetype);

  SearchResult<?> searchMime(String search, Integer firstResult, Integer maxResults, String group,
      String mimetype, String mimetype2);

  void setGadgetProperties(User user, Content content, String gadgetName, Map<String, String> properties);

  I18nLanguage setLanguage(User user, Long contentId, String languageCode) throws DefaultException;

  void setModifiedOn(Content content, long lastModifiedTime);

  void setPublishedOn(User user, Long contentId, Date publishedOn) throws DefaultException;

  Content setStatus(Long contentId, ContentStatus contentStatus);

  void setTags(User user, Long contentId, String tags) throws DefaultException;

}
