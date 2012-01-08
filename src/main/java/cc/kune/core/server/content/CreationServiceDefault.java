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

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.client.actions.xml.XMLKuneClientActions;
import cc.kune.core.client.actions.xml.XMLWaveExtension;
import cc.kune.core.server.tool.ServerTool;
import cc.kune.core.server.tool.ServerToolRegistry;
import cc.kune.core.server.tool.ServerWaveTool;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.User;
import cc.kune.wave.server.kspecific.KuneWaveService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CreationServiceDefault implements CreationService {
  private final XMLKuneClientActions actions;
  private final ContainerManager containerManager;
  private final ContentManagerDefault contentManager;
  private final Log LOG = LogFactory.getLog(CreationServiceDefault.class);
  private final ServerToolRegistry tools;

  @Inject
  public CreationServiceDefault(final ContainerManager containerManager,
      final ContentManager contentManager, final ServerToolRegistry toolRegistry,
      final XMLActionReader xmlActionReader) {
    this.containerManager = containerManager;
    this.contentManager = (ContentManagerDefault) contentManager;
    this.tools = toolRegistry;
    this.actions = xmlActionReader.getActions();
  }

  @Override
  public void addGadgetToContent(final User user, final Content content, final String gadgetName) {
    final URL gadgetUrl = getGadgetUrl(gadgetName);
    contentManager.addGadgetToContent(user, content, gadgetUrl);
  }

  @Override
  public Content createContent(final String title, final String body, final User user,
      final Container container, final String typeId) {
    final String toolName = container.getToolName();
    final ServerTool tool = tools.get(toolName);
    tool.checkTypesBeforeContentCreation(container.getTypeId(), typeId);
    final URL gagdetUrl = tool instanceof ServerWaveTool ? ((ServerWaveTool) tool).getGadgetUrl()
        : KuneWaveService.WITHOUT_GADGET;
    final Content content = contentManager.createContent(title, body, user, container, typeId, gagdetUrl);
    tool.onCreateContent(content, container);
    return content;
  }

  @Override
  public Container createFolder(final Group group, final Long parentFolderId, final String name,
      final I18nLanguage language, final String typeId) {
    final Container parent = containerManager.find(parentFolderId);
    final String toolName = parent.getToolName();
    tools.get(toolName).checkTypesBeforeContainerCreation(parent.getTypeId(), typeId);
    final Container child = containerManager.createFolder(group, parent, name, language, typeId);
    tools.get(toolName).onCreateContainer(child, parent);
    return child;
  }

  @Override
  public Content createGadget(final User user, final Container container, final String gadgetname,
      final String typeIdChild, final String title, final String body) {
    final String toolName = container.getToolName();
    final ServerTool tool = tools.get(toolName);
    tool.checkTypesBeforeContentCreation(container.getTypeId(), typeIdChild);
    final Content content = contentManager.createContent(title, body, user, container, typeIdChild,
        getGadgetUrl(gadgetname));
    tool.onCreateContent(content, container);
    return content;
  }

  @Override
  public Container createRootFolder(final Group group, final String name, final String rootName,
      final String typeRoot) {
    // FIXME Check that does not exist yet
    return containerManager.createRootFolder(group, name, rootName, typeRoot);
  }

  private URL getGadgetUrl(final String gadgetname) {
    URL gadgetUrl = null;
    final XMLWaveExtension extension = actions.getExtensions().get(gadgetname);
    assert extension != null;
    final String urlS = extension.getGadgetUrl();
    try {
      gadgetUrl = new URL(urlS);
    } catch (final MalformedURLException e) {
      LOG.error("Parsing gadget URL: " + urlS, e);
    }
    return gadgetUrl;
  }

  @Override
  public Content saveContent(final User editor, final Content content, final String body) {
    return contentManager.save(editor, content, body);
  }

}
