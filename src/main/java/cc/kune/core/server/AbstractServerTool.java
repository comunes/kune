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
package cc.kune.core.server;

import java.util.Date;
import java.util.List;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.errors.ContainerNotPermittedException;
import cc.kune.core.client.errors.ContentNotPermittedException;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerTool;
import cc.kune.core.server.tool.ServerToolRegistry;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.ToolConfiguration;
import cc.kune.domain.User;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractServerTool.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractServerTool implements ServerTool {

  /** The configuration manager. */
  protected final ToolConfigurationManager configurationManager;

  /** The container manager. */
  private final ContainerManager containerManager;

  /** The content manager. */
  private final ContentManager contentManager;

  /** The creation service. */
  protected final CreationService creationService;

  /** The i18n. */
  protected final I18nTranslationService i18n;

  /** The name. */
  private final String name;

  /** The root name. */
  private final String rootName;

  /** The target. */
  private final ServerToolTarget target;

  /** The type root. */
  private final String typeRoot;

  /** The valid container parents. */
  private final List<String> validContainerParents;

  /** The valid containers. */
  private final List<String> validContainers;

  /** The valid content parents. */
  private final List<String> validContentParents;

  /** The valid contents. */
  private final List<String> validContents;

  /**
   * Instantiates a new abstract server tool.
   * 
   * @param name
   *          the name
   * @param rootName
   *          the root name
   * @param typeRoot
   *          the type root
   * @param validContents
   *          the valid contents
   * @param validContentParents
   *          the valid content parents
   * @param validContainers
   *          the valid containers
   * @param validContainerParents
   *          the valid container parents
   * @param contentManager
   *          the content manager
   * @param containerManager
   *          the container manager
   * @param creationService
   *          the creation service
   * @param configurationManager
   *          the configuration manager
   * @param i18n
   *          the i18n
   * @param target
   *          the target
   */
  public AbstractServerTool(final String name, final String rootName, final String typeRoot,
      final List<String> validContents, final List<String> validContentParents,
      final List<String> validContainers, final List<String> validContainerParents,
      final ContentManager contentManager, final ContainerManager containerManager,
      final CreationService creationService, final ToolConfigurationManager configurationManager,
      final I18nTranslationService i18n, final ServerToolTarget target) {
    this.name = name;
    this.rootName = rootName;
    this.typeRoot = typeRoot;
    this.validContents = validContents;
    this.validContainers = validContainers;
    this.validContentParents = validContentParents;
    this.validContainerParents = validContainerParents;
    this.contentManager = contentManager;
    this.containerManager = containerManager;
    this.creationService = creationService;
    this.configurationManager = configurationManager;
    this.i18n = i18n;
    this.target = target;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.tool.ServerTool#checkTypesBeforeContainerCreation(java
   * .lang.String, java.lang.String)
   */
  @Override
  public void checkTypesBeforeContainerCreation(final String parentTypeId, final String typeId) {
    if (validContainers.contains(typeId) && validContainerParents.contains(parentTypeId)) {
      // ok
    } else {
      throw new ContainerNotPermittedException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.tool.ServerTool#checkTypesBeforeContentCreation(java
   * .lang.String, java.lang.String)
   */
  @Override
  public void checkTypesBeforeContentCreation(final String parentTypeId, final String typeId) {
    if (validContents.contains(typeId) && validContentParents.contains(parentTypeId)) {
      // ok
    } else {
      throw new ContentNotPermittedException();
    }
  }

  /**
   * Creates the initial content.
   * 
   * @param user
   *          the user
   * @param group
   *          the group
   * @param rootFolder
   *          the root folder
   * @param title
   *          the title
   * @param body
   *          the body
   * @param contentType
   *          the content type
   * @return the content
   */
  protected Content createInitialContent(final User user, final Group group, final Container rootFolder,
      final String title, final String body, final String contentType) {
    final Content content = creationService.createContent(title, body, user, rootFolder, contentType);
    setContentValues(content, contentType, user);
    contentManager.save(content);
    return content;
  }

  /**
   * Creates the root.
   * 
   * @param group
   *          the group
   * @return the container
   */
  protected Container createRoot(final Group group) {
    final ToolConfiguration config = new ToolConfiguration();

    final Container rootFolder = creationService.createRootFolder(group, name, rootName, typeRoot);
    setContainerAcl(rootFolder);
    config.setRoot(rootFolder);
    group.setToolConfig(name, config);
    configurationManager.persist(config);
    return rootFolder;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.tool.ServerTool#getName()
   */
  @Override
  public String getName() {
    return name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.tool.ServerTool#getRootName()
   */
  @Override
  public String getRootName() {
    return rootName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.tool.ServerTool#getTarget()
   */
  @Override
  public ServerToolTarget getTarget() {
    return target;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.tool.ServerTool#onCreateContainer(cc.kune.domain.Container
   * , cc.kune.domain.Container)
   */
  @Override
  public void onCreateContainer(final Container container, final Container parent) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.tool.ServerTool#onCreateContent(cc.kune.domain.Content,
   * cc.kune.domain.Container)
   */
  @Override
  public void onCreateContent(final Content content, final Container parent) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.tool.ServerTool#register(cc.kune.core.server.tool.
   * ServerToolRegistry)
   */
  @Override
  @Inject
  public void register(final ServerToolRegistry registry) {
    registry.register(this);
  }

  /**
   * Sets the access list.
   * 
   * @param container
   *          the container
   * @param acl
   *          the acl
   */
  protected void setAccessList(final Container container, final AccessLists acl) {
    containerManager.setAccessList(container, acl);
  }

  /**
   * Sets the container acl.
   * 
   * @param container
   *          the new container acl
   */
  protected void setContainerAcl(final Container container) {
  }

  /**
   * Sets the content values.
   * 
   * @param content
   *          the content
   * @param contentType
   *          the content type
   * @param author
   *          the author
   */
  private void setContentValues(final Content content, final String contentType, final User author) {
    content.addAuthor(author);
    content.setLanguage(author.getLanguage());
    content.setTypeId(contentType);
    content.setStatus(ContentStatus.publishedOnline);
    content.setPublishedOn(new Date());
  }
}
