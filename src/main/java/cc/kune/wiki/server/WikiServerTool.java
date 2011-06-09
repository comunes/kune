/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package cc.kune.wiki.server;

import static cc.kune.wiki.shared.WikiConstants.NAME;
import static cc.kune.wiki.shared.WikiConstants.ROOT_NAME;
import static cc.kune.wiki.shared.WikiConstants.TYPE_FOLDER;
import static cc.kune.wiki.shared.WikiConstants.TYPE_ROOT;
import static cc.kune.wiki.shared.WikiConstants.TYPE_UPLOADEDFILE;
import static cc.kune.wiki.shared.WikiConstants.TYPE_WIKIPAGE;

import java.util.Date;

import cc.kune.core.client.errors.ContainerNotPermittedException;
import cc.kune.core.client.errors.ContentNotPermittedException;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerTool;
import cc.kune.core.server.tool.ServerToolRegistry;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.GroupListMode;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.ToolConfiguration;
import cc.kune.domain.User;

import com.google.inject.Inject;

public class WikiServerTool implements ServerTool {

  private final ToolConfigurationManager configurationManager;
  private final ContainerManager containerManager;
  private final ContentManager contentManager;
  private final I18nTranslationService i18n;

  @Inject
  public WikiServerTool(final ContentManager contentManager, final ContainerManager containerManager,
      final ToolConfigurationManager configurationManager,
      final I18nTranslationService translationService) {
    this.contentManager = contentManager;
    this.containerManager = containerManager;
    this.configurationManager = configurationManager;
    this.i18n = translationService;
  }

  void checkContainerTypeId(final String parentTypeId, final String typeId) {
    if (typeId.equals(TYPE_FOLDER)) {
      // ok valid container
      if ((typeId.equals(TYPE_FOLDER) && (parentTypeId.equals(TYPE_ROOT) || parentTypeId.equals(TYPE_FOLDER)))) {
        // ok
      } else {
        throw new ContainerNotPermittedException();
      }
    } else {
      throw new ContainerNotPermittedException();
    }
  }

  void checkContentTypeId(final String parentTypeId, final String typeId) {
    if (typeId.equals(TYPE_WIKIPAGE) || typeId.equals(TYPE_UPLOADEDFILE)) {
      // ok valid content
      final boolean parentIsFolderOrRoot = parentTypeId.equals(TYPE_ROOT)
          || parentTypeId.equals(TYPE_FOLDER);
      if ((typeId.equals(TYPE_UPLOADEDFILE) && parentIsFolderOrRoot)
          || (typeId.equals(TYPE_WIKIPAGE) && parentIsFolderOrRoot)) {
        // ok
      } else {
        throw new ContentNotPermittedException();
      }
    } else {
      throw new ContentNotPermittedException();
    }
  }

  @Override
  public void checkTypesBeforeContainerCreation(final String parentTypeId, final String typeId) {
    checkContainerTypeId(parentTypeId, typeId);
  }

  @Override
  public void checkTypesBeforeContentCreation(final String parentTypeId, final String typeId) {
    checkContentTypeId(parentTypeId, typeId);
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getRootName() {
    return ROOT_NAME;
  }

  @Override
  public ServerToolTarget getTarget() {
    return ServerToolTarget.forBoth;
  }

  @Override
  public Group initGroup(final User user, final Group group, final Object... otherVars) {
    final ToolConfiguration config = new ToolConfiguration();
    final Container rootFolder = containerManager.createRootFolder(group, NAME, ROOT_NAME, TYPE_ROOT);
    setContainerWikiAcl(rootFolder);
    config.setRoot(rootFolder);
    group.setToolConfig(NAME, config);
    configurationManager.persist(config);
    final Content content = contentManager.createContent(i18n.t("Wiki page sample"),
        i18n.t("This is only a wiki page sample. You can edit or rename it, but also any other user."),
        user, rootFolder, TYPE_WIKIPAGE);
    content.addAuthor(user);
    content.setLanguage(user.getLanguage());
    content.setTypeId(TYPE_WIKIPAGE);
    content.setStatus(ContentStatus.publishedOnline);

    contentManager.save(content);
    return group;
  }

  @Override
  public void onCreateContainer(final Container container, final Container parent) {
    setContainerWikiAcl(container);
  }

  @Override
  public void onCreateContent(final Content content, final Container parent) {
    content.setStatus(ContentStatus.publishedOnline);
    content.setPublishedOn(new Date());
  }

  @Override
  @Inject
  public void register(final ServerToolRegistry registry) {
    registry.register(this);
  }

  private void setContainerWikiAcl(final Container container) {
    final AccessLists wikiAcl = new AccessLists();
    wikiAcl.getAdmins().setMode(GroupListMode.NORMAL);
    wikiAcl.getAdmins().add(container.getOwner());
    wikiAcl.getEditors().setMode(GroupListMode.EVERYONE);
    wikiAcl.getViewers().setMode(GroupListMode.EVERYONE);
    containerManager.setAccessList(container, wikiAcl);
  }
}
