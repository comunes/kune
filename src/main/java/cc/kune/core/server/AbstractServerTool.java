/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

public abstract class AbstractServerTool implements ServerTool {

  protected final ToolConfigurationManager configurationManager;
  private final ContainerManager containerManager;
  private final ContentManager contentManager;
  protected final CreationService creationService;
  protected final I18nTranslationService i18n;
  private final String name;
  private final String rootName;
  private final ServerToolTarget target;
  private final String typeRoot;
  private final List<String> validContainerParents;
  private final List<String> validContainers;
  private final List<String> validContentParents;
  private final List<String> validContents;

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

  @Override
  public void checkTypesBeforeContainerCreation(final String parentTypeId, final String typeId) {
    if (validContainers.contains(typeId) && validContainerParents.contains(parentTypeId)) {
      // ok
    } else {
      throw new ContainerNotPermittedException();
    }
  }

  @Override
  public void checkTypesBeforeContentCreation(final String parentTypeId, final String typeId) {
    if (validContents.contains(typeId) && validContentParents.contains(parentTypeId)) {
      // ok
    } else {
      throw new ContentNotPermittedException();
    }
  }

  protected Content createInitialContent(final User user, final Group group, final Container rootFolder,
      final String title, final String body, final String contentType) {
    final Content content = creationService.createContent(title, body, user, rootFolder, contentType);
    setContentValues(content, contentType, user);
    contentManager.save(content);
    return content;
  }

  protected Container createRoot(final Group group) {
    final ToolConfiguration config = new ToolConfiguration();

    final Container rootFolder = creationService.createRootFolder(group, name, rootName, typeRoot);
    setContainerAcl(rootFolder);
    config.setRoot(rootFolder);
    group.setToolConfig(name, config);
    configurationManager.persist(config);
    return rootFolder;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getRootName() {
    return rootName;
  }

  @Override
  public ServerToolTarget getTarget() {
    return target;
  }

  @Override
  public void onCreateContainer(final Container container, final Container parent) {
  }

  @Override
  public void onCreateContent(final Content content, final Container parent) {
  }

  @Override
  @Inject
  public void register(final ServerToolRegistry registry) {
    registry.register(this);
  }

  protected void setAccessList(final Container container, final AccessLists acl) {
    containerManager.setAccessList(container, acl);
  }

  protected void setContainerAcl(final Container container) {
  }

  private void setContentValues(final Content content, final String contentType, final User author) {
    content.addAuthor(author);
    content.setLanguage(author.getLanguage());
    content.setTypeId(contentType);
    content.setStatus(ContentStatus.publishedOnline);
    content.setPublishedOn(new Date());
  }
}
