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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.QueryParser;
import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.model.waveref.WaveRef;
import org.waveprotocol.wave.util.escapers.jvm.JavaWaverefEncoder;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.xml.XMLWaveExtension;
import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.I18nNotFoundException;
import cc.kune.core.client.errors.MoveOnSameContainerException;
import cc.kune.core.client.errors.NameInUseException;
import cc.kune.core.client.errors.UserNotFoundException;
import cc.kune.core.server.access.FinderService;
import cc.kune.core.server.manager.SearchResult;
import cc.kune.core.server.manager.TagUserContentManager;
import cc.kune.core.server.manager.file.FileUtils;
import cc.kune.core.server.manager.impl.DefaultManager;
import cc.kune.core.server.manager.impl.GroupServerUtils;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.core.server.tool.ServerTool;
import cc.kune.core.server.tool.ServerToolRegistry;
import cc.kune.core.server.utils.FilenameUtils;
import cc.kune.core.shared.domain.AccessRol;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.core.shared.domain.RateResult;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.GroupList;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.Rate;
import cc.kune.domain.Revision;
import cc.kune.domain.User;
import cc.kune.domain.finders.ContainerFinder;
import cc.kune.domain.finders.ContentFinder;
import cc.kune.domain.finders.I18nLanguageFinder;
import cc.kune.domain.finders.UserFinder;
import cc.kune.events.server.utils.EventsCache;
import cc.kune.events.shared.EventsToolConstants;
import cc.kune.trash.server.TrashServerUtils;
import cc.kune.trash.shared.TrashToolConstants;
import cc.kune.wave.server.kspecific.KuneWaveServerUtils;
import cc.kune.wave.server.kspecific.KuneWaveService;
import cc.kune.wave.server.kspecific.ParticipantUtils;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.wave.api.Participants;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentManagerDefault.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class ContentManagerDefault extends DefaultManager<Content, Long> implements ContentManager {
  private final ContainerFinder containerFinder;
  private final ContentFinder contentFinder;
  private final EventsCache eventsCache;
  private final FinderService finder;
  private final I18nTranslationService i18n;
  private final KuneWaveService kuneWaveManager;
  private final I18nLanguageFinder languageFinder;
  private final Log LOG = LogFactory.getLog(ContentManagerDefault.class);
  private final ParticipantUtils participantUtils;
  private final TagUserContentManager tagManager;
  private final ServerToolRegistry tools;
  private final UserFinder userFinder;
  private final XMLActionReader xmlActionReader;

  @Inject
  public ContentManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final ContentFinder contentFinder, final ContainerFinder containerFinder,
      final FinderService finder, final UserFinder userFinder, final I18nLanguageFinder languageFinder,
      final TagUserContentManager tagManager, final KuneWaveService kuneWaveManager,
      final ParticipantUtils participantUtils, final ServerToolRegistry tools,
      final XMLActionReader xmlActionReader, final I18nTranslationService i18n,
      final EventsCache eventsCache) {
    super(provider, Content.class);
    this.contentFinder = contentFinder;
    this.containerFinder = containerFinder;
    this.finder = finder;
    this.userFinder = userFinder;
    this.languageFinder = languageFinder;
    this.tagManager = tagManager;
    this.kuneWaveManager = kuneWaveManager;
    this.participantUtils = participantUtils;
    this.tools = tools;
    this.xmlActionReader = xmlActionReader;
    this.i18n = i18n;
    this.eventsCache = eventsCache;
  }

  @Override
  public void addAuthor(final User user, final Long contentId, final String authorShortName)
      throws DefaultException {
    final Content content = finder.getContent(contentId);
    final User author = userFinder.findByShortName(authorShortName);
    if (author == null) {
      throw new UserNotFoundException();
    }
    content.addAuthor(author);
  }

  @Override
  public void addGadgetToContent(final User user, final Content content, final String gadgetName) {
    final URL gadgetUrl = getGadgetUrl(gadgetName);
    addGadgetToContent(user, content, gadgetUrl);
  }

  public void addGadgetToContent(final User user, final Content content, final URL gadgetUrl) {
    kuneWaveManager.addGadget(KuneWaveServerUtils.getWaveRef(content),
        participantUtils.of(user.getShortName()).toString(), gadgetUrl);
  }

  @Override
  public boolean addParticipant(final User user, final Long contentId, final String participant) {
    final Content content = finder.getContent(contentId);
    return addParticipants(user, content, participant);
  }

  /**
   * Adds the participant to a wave
   * 
   * @param user
   *          the user
   * @param content
   *          the content
   * @param participant
   *          the participant
   * @return true, if successful added (not already participant)
   */
  private boolean addParticipants(final User user, final Content content, final String... participants) {
    if (content.isWave()) {
      return kuneWaveManager.addParticipants(KuneWaveServerUtils.getWaveRef(content),
          getContentAuthor(content), user.getShortName(), participants);
    }
    return false;
  }

  @Override
  public boolean addParticipants(final User user, final Long contentId, final Group group,
      final SocialNetworkSubGroup whichOnes) {
    final Set<String> members = participantsGroupToSet(group, whichOnes);
    return addParticipants(user, finder.getContent(contentId),
        members.toArray(new String[members.size()]));
  }

  @Override
  public Content addToAcl(final Content content, final Group group, final AccessRol rol) {
    // FIXME: we should remove the previous group from other previous lists
    if (true) {
      throw new RuntimeException("In development");
    }
    final AccessLists acl = content.getAccessLists();
    final GroupList list = getListFromRol(rol, acl);
    list.add(group);
    acl.setList(rol, list);
    save(content);
    return content;
  }

  private void clearEventsCacheIfNecessary(final Container previousParent) {
    if (previousParent.getToolName().equals(EventsToolConstants.TOOL_NAME)) {
      eventsCache.remove(previousParent);
    }
  }

  @Override
  public Content copyContent(final User user, final Container destination, final Content contentToCopy) {
    try {
      return createContent(
          findInexistentTitle(destination, i18n.t("Copy of [%s]", contentToCopy.getTitle())), "",
          JavaWaverefEncoder.decodeWaveRefFromPath(contentToCopy.getWaveId()), user, destination,
          contentToCopy.getTypeId(), KuneWaveService.WITHOUT_GADGET,
          Collections.<String, String> emptyMap());
    } catch (final InvalidWaveRefException e) {
      throw new DefaultException("Error copying wave", e);
    }
  }

  protected Content createContent(final String title, final String body, final User author,
      final Container container, final String typeId) {
    return createContent(title, body, KuneWaveService.NO_WAVE_TO_COPY, author, container, typeId,
        KuneWaveService.WITHOUT_GADGET, Collections.<String, String> emptyMap());
  }

  protected Content createContent(final String title, final String body, final WaveRef waveIdToCopy,
      final User author, final Container container, final String typeId, final URL gadgetUrl,
      final Map<String, String> gadgetProperties, final String... otherParticipants) {
    FilenameUtils.checkBasicFilename(title);
    final String newtitle = findInexistentTitle(container, title);
    final Content newContent = new Content();
    newContent.addAuthor(author);
    newContent.setLanguage(author.getLanguage());
    newContent.setTypeId(typeId);
    container.addContent(newContent);
    newContent.setContainer(container);
    final Revision revision = new Revision(newContent);
    revision.setTitle(newtitle);
    // Duplicate in StateServiceDefault
    if (newContent.isWave()) {
      final String authorName = author.getShortName();
      final WaveRef waveRef = kuneWaveManager.createWave(newtitle, body, waveIdToCopy,
          KuneWaveService.DO_NOTHING_CBACK, gadgetUrl, gadgetProperties,
          participantUtils.of(authorName, otherParticipants));
      newContent.setWaveId(JavaWaverefEncoder.encodeToUriPathSegment(waveRef));
      newContent.setModifiedOn((new Date()).getTime());
    }
    revision.setBody(body);
    newContent.addRevision(revision);
    return save(newContent);
  }

  @Override
  public Content createGadget(final User user, final Container container, final String gadgetname,
      final String typeIdChild, final String title, final String body,
      final Map<String, String> gadgetProperties) {
    final String toolName = container.getToolName();
    final ServerTool tool = tools.get(toolName);
    tool.checkTypesBeforeContentCreation(container.getTypeId(), typeIdChild);
    final Content content = createContent(title, body, KuneWaveService.NO_WAVE_TO_COPY, user, container,
        typeIdChild, getGadgetUrl(gadgetname), gadgetProperties);
    tool.onCreateContent(content, container);
    return content;
  }

  private boolean delParticipants(final User user, final Content content, final String... participants) {
    if (content.isWave()) {
      // The author cannot be removed (by now)
      final String author = getContentAuthor(content);
      final Set<String> participantsSet = participantUtils.toSet(participants);
      participantsSet.remove(author);
      final WaveRef waveRef = KuneWaveServerUtils.getWaveRef(content);
      return kuneWaveManager.delParticipants(waveRef, user.getShortName(), participantsSet);
    }
    return false;
  }

  @Override
  public boolean delParticipants(final User user, final Long contentId, final String... participants) {
    return delParticipants(user, finder.getContent(contentId), participants);
  }

  @Override
  public boolean delPublicParticipant(final User user, final Long contentId) {
    return delParticipants(user, finder.getContent(contentId), this.getPublicParticipant());
  }

  @Override
  public boolean findIfExistsTitle(final Container container, final String title) {
    return (contentFinder.findIfExistsTitle(container, title) > 0)
        || (containerFinder.findIfExistsTitle(container, title) > 0);
  }

  private String findInexistentTitle(final Container container, final String title) {
    String initialTitle = String.valueOf(title);
    while (findIfExistsTitle(container, initialTitle)) {
      initialTitle = FileUtils.getNextSequentialFileName(initialTitle);
    }
    return initialTitle;
  }

  private String getContentAuthor(final Content content) {
    return content.getAuthors().get(0).getShortName();
  }

  private URL getGadgetUrl(final String gadgetname) {
    URL gadgetUrl = null;
    final XMLWaveExtension extension = xmlActionReader.getActions().getExtensions().get(gadgetname);
    assert extension != null;
    final String urlS = extension.getGadgetUrl();
    try {
      gadgetUrl = new URL(urlS);
    } catch (final MalformedURLException e) {
      LOG.error("Parsing gadget URL: " + urlS, e);
    }
    return gadgetUrl;
  }

  private GroupList getListFromRol(final AccessRol rol, final AccessLists acl) {
    GroupList list;
    switch (rol) {
    case Administrator:
      list = acl.getAdmins();
      break;
    case Editor:
      list = acl.getEditors();
      break;
    case Viewer:
      list = acl.getViewers();
      break;
    default:
      throw new DefaultException("Error setting ACL");
    }
    return list;
  }

  private String getPublicParticipant() {
    return participantUtils.getPublicParticipantId().toString();
  }

  @Override
  public Double getRateAvg(final Content content) {
    return finder.getRateAvg(content);
  }

  @Override
  public Long getRateByUsers(final Content content) {
    return finder.getRateByUsers(content);
  }

  @Override
  public Double getRateContent(final User rater, final Content content) {
    final Rate rate = finder.getRate(rater, content);
    if (rate != null) {
      return rate.getValue();
    } else {
      return null;
    }
  }

  @Override
  public Content moveContent(final Content content, final Container newContainer) {
    if (newContainer.equals(content.getContainer())) {
      throw new MoveOnSameContainerException();
    }
    final String title = content.getTitle();
    if (findIfExistsTitle(newContainer, title)) {
      throw new NameInUseException();
    }
    final Container oldContainer = content.getContainer();
    oldContainer.removeContent(content);
    newContainer.addContent(content);
    content.setContainer(newContainer);
    clearEventsCacheIfNecessary(oldContainer);
    return persist(content);
  }

  private Set<String> participantsGroupToSet(final Group group, final SocialNetworkSubGroup whichOnes) {
    final Set<String> members = new HashSet<String>();
    if (whichOnes.equals(SocialNetworkSubGroup.PUBLIC)) {
      members.add(getPublicParticipant());
    } else {
      GroupServerUtils.getAllUserMembersAsString(members, group, whichOnes);
    }
    return members;
  }

  @Override
  public Container purgeAll(final Container container) {
    Preconditions.checkState(container.isRoot(), "Trying to purge a non root folder: " + container);
    Preconditions.checkState(container.getTypeId().equals(TrashToolConstants.TYPE_ROOT),
        "Container is not a trash root folder");

    // Issue #256 (ConcurrentModificationException)
    // http://www.javacodegeeks.com/2011/05/avoid-concurrentmodificationexception.html
    final Set<Content> contents = container.getContents();
    final Content[] contentsArray = contents.toArray(new Content[contents.size()]);
    // Set to null
    container.setContents(new HashSet<Content>());
    for (final Content content : contentsArray) {
      purgeContent(content);
    }
    return container;
  }

  /**
   * Purge content (permanent delete)
   * 
   * @param content
   *          the content to purge
   */
  @Override
  public Container purgeContent(final Content content) {
    if (!TrashServerUtils.inTrash(content)) {
      throw new AccessViolationException("Trying to purge a not deleted content:" + content);
    }
    final WaveRef waveRef = KuneWaveServerUtils.getWaveRef(content);
    if (content.isWave()) {
      final String author = getContentAuthor(content);
      try {
        final Participants participants = kuneWaveManager.getParticipants(waveRef, author);
        if (participants.contains(author)) {
          kuneWaveManager.delParticipants(waveRef, author,
              participantUtils.arrayFromOrdered(participants, author));
        } else {
          // We cannot delete a wave we are not participating
        }
      } catch (final Exception e) {
        LOG.error(String.format("Error accessing for deleting content %s and wave %s",
            content.getStateToken(), waveRef.toString()), e);
        // We delete the content anyway
      }
    }
    content.authorsClear();
    final Container container = content.getContainer();
    container.removeContent(content);
    remove(content);
    return container;
  }

  @Override
  public RateResult rateContent(final User rater, final Long contentId, final Double value)
      throws DefaultException {
    final Content content = finder.getContent(contentId);
    final Rate oldRate = finder.getRate(rater, content);
    if (oldRate == null) {
      final Rate rate = new Rate(rater, content, value);
      super.persist(rate, Rate.class);
    } else {
      oldRate.setValue(value);
      super.persist(oldRate, Rate.class);
    }
    final Double rateAvg = getRateAvg(content);
    final Long rateByUsers = getRateByUsers(content);
    return new RateResult(rateAvg != null ? rateAvg : 0D, rateByUsers != null ? rateByUsers.intValue()
        : 0, value);
  }

  @Override
  public void removeAuthor(final User user, final Long contentId, final String authorShortName)
      throws DefaultException {
    final Content content = finder.getContent(contentId);
    final User author = userFinder.findByShortName(authorShortName);
    if (author == null) {
      throw new UserNotFoundException();
    }
    content.removeAuthor(author);
  }

  @Override
  public Content removeFromAcl(final Content content, final Group group, final AccessRol rol) {
    final AccessLists acl = content.getAccessLists();
    final GroupList list = getListFromRol(rol, acl);
    list.remove(group);
    acl.setList(rol, list);
    save(content);
    return content;
  }

  @Override
  public Content renameContent(final User user, final Long contentId, final String newTitle)
      throws DefaultException {
    final String newTitleWithoutNL = FilenameUtils.chomp(newTitle);
    FilenameUtils.checkBasicFilename(newTitleWithoutNL);
    final Content content = finder.getContent(contentId);
    if (findIfExistsTitle(content.getContainer(), newTitleWithoutNL)) {
      throw new NameInUseException();
    }
    content.getLastRevision().setTitle(newTitleWithoutNL);
    if (content.isWave()) {
      kuneWaveManager.setTitle(KuneWaveServerUtils.getWaveRef(content), newTitle,
          getContentAuthor(content));
    }
    setModifiedTime(content);
    clearEventsCacheIfNecessary(content.getContainer());
    return content;
  }

  @Override
  public Content save(final Content content) {
    setModifiedTime(content);
    return persist(content);
  }

  @Override
  public Content save(final User editor, final Content content, final String body) {
    setModifiedTime(content);
    final Revision revision = new Revision(content);
    revision.setEditor(editor);
    revision.setTitle(content.getTitle());
    revision.setBody(body);
    content.addRevision(revision);
    return persist(content);
  }

  @Override
  public SearchResult<Content> search(final String search) {
    return this.search(search, null, null);
  }

  @Override
  public SearchResult<Content> search(final String search, final Integer firstResult,
      final Integer maxResults) {
    final String escapedQuery = QueryParser.escape(search);
    return super.search(new String[] { escapedQuery, escapedQuery, escapedQuery, escapedQuery,
        escapedQuery, escapedQuery, escapedQuery, escapedQuery }, new String[] { "authors.name",
        "authors.shortName", "container.name", "language.code", "language.englishName",
        "language.nativeName", "lastRevision.body", "lastRevision.title" }, firstResult, maxResults);
  }

  @Override
  public SearchResult<Content> searchMime(final String search, final Integer firstResult,
      final Integer maxResults, final String groupShortName, final String mimetype) {
    final List<Content> list = contentFinder.findMime(groupShortName, "%" + search + "%", mimetype,
        firstResult, maxResults);
    final Long count = contentFinder.findMimeCount(groupShortName, "%" + search + "%", mimetype);
    return new SearchResult<Content>(count.intValue(), list);
  }

  @Override
  public SearchResult<?> searchMime(final String search, final Integer firstResult,
      final Integer maxResults, final String groupShortName, final String mimetype,
      final String mimetype2) {
    final List<Content> list = contentFinder.find2Mime(groupShortName, "%" + search + "%", mimetype,
        mimetype2, firstResult, maxResults);
    final Long count = contentFinder.find2MimeCount(groupShortName, "%" + search + "%", mimetype,
        mimetype2);
    return new SearchResult<Content>(count.intValue(), list);
  }

  @Override
  public Content setAclMode(final Content content, final AccessRol rol, final GroupListMode mode) {
    final AccessLists acl = content.getAccessLists();
    // Group group = content.getContainer().getOwner();
    final GroupList list = getListFromRol(rol, acl);
    if (AccessRol.Administrator.equals(rol) && !GroupListMode.NORMAL.equals(mode)) {
      throw new RuntimeException("Cannot set to admins other mode than normal");
    }
    list.setMode(mode);
    acl.setList(rol, list);
    save(content);
    return content;
  }

  @Override
  public void setGadgetProperties(final User user, final Content content, final String gadgetName,
      final Map<String, String> properties) {
    final URL gadgetUrl = getGadgetUrl(gadgetName);
    if (gadgetName.equals(EventsToolConstants.TYPE_MEETING_DEF_GADGETNAME)) {
      eventsCache.remove(content.getContainer());
    }
    kuneWaveManager.setGadgetProperty(KuneWaveServerUtils.getWaveRef(content),
        getContentAuthor(content), gadgetUrl, properties);
  }

  @Override
  public I18nLanguage setLanguage(final User user, final Long contentId, final String languageCode)
      throws DefaultException {
    final Content content = finder.getContent(contentId);
    final I18nLanguage language = languageFinder.findByCode(languageCode);
    if (language == null) {
      throw new I18nNotFoundException();
    }
    content.setLanguage(language);
    return language;
  }

  @Override
  public void setModifiedOn(final Content content, final long lastModifiedTime) {
    content.setModifiedOn(lastModifiedTime);
  }

  private void setModifiedTime(final Content content) {
    setModifiedOn(content, System.currentTimeMillis());
  }

  @Override
  public void setPublishedOn(final User user, final Long contentId, final Date publishedOn)
      throws DefaultException {
    final Content content = finder.getContent(contentId);
    content.setPublishedOn(publishedOn);
  }

  @Override
  public Content setStatus(final Long contentId, final ContentStatus status) {
    final Content content = finder.getContent(contentId);
    content.setStatus(status);
    switch (status) {
    case publishedOnline:
      content.setPublishedOn(new Date());
      content.setDeletedOn(null);
      break;
    case inTheDustbin:
      content.setDeletedOn(new Date());
      content.setPublishedOn(null);
      break;
    default:
      break;
    }
    return content;
  }

  @Override
  public void setTags(final User user, final Long contentId, final String tags) throws DefaultException {
    final Content content = finder.getContent(contentId);
    tagManager.setTags(user, content, tags);
  }

  @Override
  public Content setVisible(final Content content, final boolean visible) {
    final AccessLists acl = content.getAccessLists();
    final GroupList list = getListFromRol(AccessRol.Viewer, acl);
    list.setMode(visible ? GroupListMode.EVERYONE : GroupListMode.NORMAL);
    acl.setList(AccessRol.Viewer, list);
    save(content);
    return content;
  }

}
