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

import javax.persistence.EntityManager;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;

import cc.kune.core.client.errors.AccessViolationException;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.MoveOnSameContainerException;
import cc.kune.core.client.errors.NameInUseException;
import cc.kune.core.server.manager.SearchResult;
import cc.kune.core.server.manager.file.FileUtils;
import cc.kune.core.server.manager.impl.DefaultManager;
import cc.kune.core.server.manager.impl.ServerManagerException;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.core.server.utils.FilenameUtils;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.Group;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.finders.ContainerFinder;
import cc.kune.domain.finders.ContentFinder;
import cc.kune.trash.shared.TrashToolConstants;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ContainerManagerDefault extends DefaultManager<Container, Long> implements ContainerManager {

  private final ContainerFinder containerFinder;
  private final ContentFinder contentFinder;

  @Inject
  public ContainerManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final ContentFinder contentFinder, final ContainerFinder containerFinder) {
    super(provider, Container.class);
    this.contentFinder = contentFinder;
    this.containerFinder = containerFinder;
  }

  @Override
  public Container createFolder(final Group group, final Container parent, final String name,
      final I18nLanguage language, final String typeId) {
    FilenameUtils.checkBasicFilename(name);
    final String newtitle = findInexistentName(parent, name);
    final Container child = new Container(newtitle, group, parent.getToolName());
    child.setLanguage(language);
    child.setTypeId(typeId);
    parent.addChild(child);
    persist(child);
    return child;
  }

  @Override
  public Container createRootFolder(final Group group, final String toolName, final String name,
      final String rootType) {
    final Container container = new Container(name, group, toolName);
    container.setTypeId(rootType);
    return persist(container);
  }

  /** Duplicate code in ContentMD **/
  @Override
  public boolean findIfExistsTitle(final Container container, final String title) {
    return (contentFinder.findIfExistsTitle(container, title) > 0)
        || (containerFinder.findIfExistsTitle(container, title) > 0);
  }

  /** Duplicate code in ContentMD **/
  private String findInexistentName(final Container container, final String title) {
    String initialTitle = String.valueOf(title);
    while (findIfExistsTitle(container, initialTitle)) {
      initialTitle = FileUtils.getNextSequentialFileName(initialTitle);
    }
    return initialTitle;
  }

  @Override
  public Container getTrashFolder(final Group group) {
    return containerFinder.findTypeId(group, TrashToolConstants.TYPE_ROOT);
  }

  @Override
  public boolean hasTrashFolder(final Group group) {
    return containerFinder.findIfExistsTypeId(group, TrashToolConstants.TYPE_ROOT) > 0;
  }

  @Override
  public void moveContainer(final Container container, final Container newContainer) {
    if (newContainer.equals(container.getParent())) {
      throw new MoveOnSameContainerException();
    }
    final String title = container.getName();
    if (findIfExistsTitle(newContainer, title)) {
      throw new NameInUseException();
    }
    if (container.isRoot()) {
      // Cannot move root container
      throw new AccessViolationException();
    }
    final Container oldContainer = container.getParent();
    oldContainer.removeChild(container);
    newContainer.addChild(container);
    persist(container);
  }

  @Override
  public Container renameFolder(final Group group, final Container container, final String newName)
      throws DefaultException {
    FilenameUtils.checkBasicFilename(newName);
    final String newNameWithoutNT = FilenameUtils.chomp(newName);
    if (container.isRoot()) {
      throw new DefaultException("Root folder cannot be renamed");
    }
    if (findIfExistsTitle(container.getParent(), newNameWithoutNT)) {
      throw new NameInUseException();
    }
    container.setName(newNameWithoutNT);
    persist(container);
    return container;
  }

  @Override
  public SearchResult<Container> search(final String search) {
    return this.search(search, null, null);
  }

  @Override
  public SearchResult<Container> search(final String search, final Integer firstResult,
      final Integer maxResults) {
    final MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] { "name" },
        new StandardAnalyzer());
    Query query;
    try {
      query = parser.parse(search);
    } catch (final ParseException e) {
      throw new ServerManagerException("Error parsing search");
    }
    return super.search(query, firstResult, maxResults);
  }

  @Override
  public void setAccessList(final Container container, final AccessLists accessList) {
    container.setAccessLists(accessList);
    persist(container);
  }
}
