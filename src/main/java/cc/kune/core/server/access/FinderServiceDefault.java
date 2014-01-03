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
package cc.kune.core.server.access;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.GroupNotFoundException;
import cc.kune.core.client.errors.NoDefaultContentException;
import cc.kune.core.client.errors.ToolNotFoundException;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.ContentUtils;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.RateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.Rate;
import cc.kune.domain.User;
import cc.kune.domain.finders.ContentFinder;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class FinderServiceDefault.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class FinderServiceDefault implements FinderService {

  /** The container manager. */
  private final ContainerManager containerManager;

  /** The content finder. */
  private final ContentFinder contentFinder;

  /** The content manager. */
  private final ContentManager contentManager;

  /** The group manager. */
  private final GroupManager groupManager;

  /** The rate manager. */
  private final RateManager rateManager;

  /**
   * Instantiates a new finder service default.
   * 
   * @param groupManager
   *          the group manager
   * @param containerManager
   *          the container manager
   * @param contentManager
   *          the content manager
   * @param rateManager
   *          the rate manager
   * @param contentFinder
   *          the content finder
   */
  @Inject
  public FinderServiceDefault(final GroupManager groupManager, final ContainerManager containerManager,
      final ContentManager contentManager, final RateManager rateManager,
      final ContentFinder contentFinder) {
    this.groupManager = groupManager;
    this.containerManager = containerManager;
    this.contentManager = contentManager;
    this.rateManager = rateManager;
    this.contentFinder = contentFinder;
  }

  /**
   * Check and parse.
   * 
   * @param s
   *          the s
   * @return the long
   * @throws ContentNotFoundException
   *           the content not found exception
   */
  public Long checkAndParse(final String s) throws ContentNotFoundException {
    if (s != null) {
      try {
        return Long.parseLong(s);
      } catch (final NumberFormatException e) {
        throw new ContentNotFoundException();
      }
    }
    return null;
  }

  /**
   * Check folder id.
   * 
   * @param folderId
   *          the folder id
   * @param container
   *          the container
   */
  private void checkFolderId(final Long folderId, final Container container) {
    if (!container.getId().equals(folderId)) {
      throw new ContentNotFoundException();
    }
  }

  /**
   * Check group.
   * 
   * @param groupName
   *          the group name
   * @param container
   *          the container
   */
  private void checkGroup(final String groupName, final Container container) {
    if (!container.getOwner().getShortName().equals(groupName)) {
      throw new ContentNotFoundException();
    }
  }

  /**
   * Check tool.
   * 
   * @param toolName
   *          the tool name
   * @param container
   *          the container
   */
  private void checkTool(final String toolName, final Container container) {
    if (!container.getToolName().equals(toolName)) {
      throw new ContentNotFoundException();
    }
  }

  /**
   * Find by content reference.
   * 
   * @param groupName
   *          the group name
   * @param toolName
   *          the tool name
   * @param folderId
   *          the folder id
   * @param contentId
   *          the content id
   * @return the content
   * @throws ContentNotFoundException
   *           the content not found exception
   */
  private Content findByContentReference(final String groupName, final String toolName,
      final Long folderId, final Long contentId) throws ContentNotFoundException {
    final Content content = contentManager.find(contentId);
    if (content == null) {
      throw new ContentNotFoundException();
    }
    final Container container = content.getContainer();

    checkFolderId(folderId, container);
    checkTool(toolName, container);
    checkGroup(groupName, container);

    return content;
  }

  /**
   * Find by folder reference.
   * 
   * @param groupName
   *          the group name
   * @param toolName
   *          the tool name
   * @param folderId
   *          the folder id
   * @return the content
   */
  private Content findByFolderReference(final String groupName, final String toolName,
      final Long folderId) {
    final Container container = containerManager.find(folderId);
    if (container == null) {
      throw new ContentNotFoundException();
    }

    checkFolderId(folderId, container);
    checkTool(toolName, container);
    checkGroup(groupName, container);

    return generateFolderFakeContent(container);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.access.FinderService#findByRootOnGroup(java.lang.String
   * , java.lang.String)
   */
  @Override
  public Content findByRootOnGroup(final String groupName, final String toolName)
      throws DefaultException {
    try {
      final Group group = groupManager.findByShortName(groupName);
      if (!group.existToolConfig(toolName)) {
        throw new ToolNotFoundException();
      }
      final Container container = group.getRoot(toolName);
      return generateFolderFakeContent(container);
    } catch (final NoResultException e) {
      throw new GroupNotFoundException();
    }
  }

  /**
   * Find default content of group.
   * 
   * @param group
   *          the group
   * @return the content
   */
  private Content findDefaultContentOfGroup(final Group group) {
    final Content defaultContent = group.getDefaultContent();
    if (defaultContent == null) {
      throw new NoDefaultContentException();
    } else {
      return defaultContent;
    }
  }

  /**
   * Find default content of group.
   * 
   * @param groupName
   *          the group name
   * @return the content
   * @throws GroupNotFoundException
   *           the group not found exception
   */
  private Content findDefaultContentOfGroup(final String groupName) throws GroupNotFoundException {
    final Group group = groupManager.findByShortName(groupName);
    return findDefaultContentOfGroup(group);
  }

  /**
   * Generate folder fake content.
   * 
   * @param container
   *          the container
   * @return the content
   */
  private Content generateFolderFakeContent(final Container container) {
    final Content content = new Content();
    content.setContainer(container);
    return content;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.access.FinderService#getContainer(java.lang.Long)
   */
  @Override
  public Container getContainer(final Long folderId) throws DefaultException {
    return getFolder(folderId);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.access.FinderService#getContainer(java.lang.String)
   */
  @Override
  public Container getContainer(final String folderId) throws DefaultException {
    return getContainer(ContentUtils.parseId(folderId));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.access.FinderService#getContainerByWaveRef(java.lang
   * .String)
   */
  @Override
  public Content getContainerByWaveRef(final String waveRef) {
    return contentFinder.findByWaveId(waveRef);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.access.FinderService#getContent(java.lang.Long)
   */
  @Override
  public Content getContent(final Long contentId) throws ContentNotFoundException {
    try {
      return contentManager.find(contentId);
    } catch (final PersistenceException e) {
      throw new ContentNotFoundException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.access.FinderService#getContent(java.lang.String)
   */
  @Override
  public Content getContent(final String contentId) throws ContentNotFoundException {
    return getContent(ContentUtils.parseId(contentId));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.access.FinderService#getContentOrDefContent(cc.kune
   * .core.shared.domain.utils.StateToken, cc.kune.domain.Group)
   */
  @Override
  public Content getContentOrDefContent(final StateToken token, final Group defaultGroup)
      throws DefaultException {
    final Long contentId = checkAndParse(token.getDocument());
    final Long folderId = checkAndParse(token.getFolder());

    final String group = token.getGroup();
    final String tool = token.getTool();
    if (token.hasAll()) {
      return findByContentReference(group, tool, folderId, contentId);
    } else if (token.hasGroupToolAndFolder()) {
      return findByFolderReference(group, tool, folderId);
    } else if (token.hasGroupAndTool()) {
      return findByRootOnGroup(group, tool);
    } else if (token.hasGroup()) {
      return findDefaultContentOfGroup(group);
    } else if (token.hasNothing()) {
      return findDefaultContentOfGroup(defaultGroup);
    } else {
      throw new ContentNotFoundException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.access.FinderService#getFolder(java.lang.Long)
   */
  @Override
  public Container getFolder(final Long folderId) throws ContentNotFoundException {
    try {
      return containerManager.find(folderId);
    } catch (final PersistenceException e) {
      throw new ContentNotFoundException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.access.FinderService#getRate(cc.kune.domain.User,
   * cc.kune.domain.Content)
   */
  @Override
  public Rate getRate(final User user, final Content content) {
    return rateManager.find(user, content);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.access.FinderService#getRateAvg(cc.kune.domain.Content)
   */
  @Override
  public Double getRateAvg(final Content content) {

    return rateManager.getRateAvg(content);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.access.FinderService#getRateByUsers(cc.kune.domain.
   * Content)
   */
  @Override
  public Long getRateByUsers(final Content content) {
    return rateManager.getRateByUsers(content);
  }

}
