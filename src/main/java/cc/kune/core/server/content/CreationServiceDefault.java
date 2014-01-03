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

import java.net.URL;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import cc.kune.core.server.tool.ServerTool;
import cc.kune.core.server.tool.ServerToolRegistry;
import cc.kune.core.server.tool.ServerToolWithWave;
import cc.kune.core.server.tool.ServerToolWithWaveGadget;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.User;
import cc.kune.wave.server.kspecific.KuneWaveService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class CreationServiceDefault.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class CreationServiceDefault implements CreationService {

  /** The Constant NO_MORE_PARTICIPANTS. */
  private static final String[] NO_MORE_PARTICIPANTS = ArrayUtils.EMPTY_STRING_ARRAY;

  /** The Constant NO_PROPERTIES. */
  private static final Map<String, String> NO_PROPERTIES = Collections.<String, String> emptyMap();

  /** The container manager. */
  private final ContainerManager containerManager;

  /** The content manager. */
  private final ContentManagerDefault contentManager;

  /** The tools. */
  private final ServerToolRegistry tools;

  /**
   * Instantiates a new creation service default.
   * 
   * @param containerManager
   *          the container manager
   * @param contentManager
   *          the content manager
   * @param toolRegistry
   *          the tool registry
   */
  @Inject
  public CreationServiceDefault(final ContainerManager containerManager,
      final ContentManager contentManager, final ServerToolRegistry toolRegistry) {
    this.containerManager = containerManager;
    this.contentManager = (ContentManagerDefault) contentManager;
    this.tools = toolRegistry;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.content.CreationService#copyContent(cc.kune.domain.
   * User, cc.kune.domain.Container, cc.kune.domain.Content)
   */
  @Override
  public Content copyContent(final User user, final Container container, final Content contentToCopy) {
    final ServerTool tool = tools.get(container.getToolName());
    final Content content = contentManager.copyContent(user, container, contentToCopy);
    tool.onCreateContent(content, container);
    return content;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.content.CreationService#createContent(java.lang.String,
   * java.lang.String, cc.kune.domain.User, cc.kune.domain.Container,
   * java.lang.String)
   */
  @Override
  public Content createContent(final String title, final String body, final User user,
      final Container container, final String typeId) {
    final ServerTool tool = tools.get(container.getToolName());
    tool.checkTypesBeforeContentCreation(container.getTypeId(), typeId);
    final URL gagdetUrl = tool instanceof ServerToolWithWaveGadget ? ((ServerToolWithWaveGadget) tool).getGadgetUrl()
        : KuneWaveService.WITHOUT_GADGET;
    final String[] otherParticipants = tool instanceof ServerToolWithWave ? ((ServerToolWithWave) tool).getNewContentAdditionalParts(container)
        : NO_MORE_PARTICIPANTS;
    final Content content = contentManager.createContent(title, body, KuneWaveService.NO_WAVE_TO_COPY,
        user, container, typeId, gagdetUrl, NO_PROPERTIES, otherParticipants);
    tool.onCreateContent(content, container);
    return content;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.content.CreationService#createFolder(cc.kune.domain
   * .Group, java.lang.Long, java.lang.String, cc.kune.domain.I18nLanguage,
   * java.lang.String)
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.content.CreationService#createRootFolder(cc.kune.domain
   * .Group, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public Container createRootFolder(final Group group, final String name, final String rootName,
      final String typeRoot) {
    // FIXME Check that does not exist yet
    return containerManager.createRootFolder(group, name, rootName, typeRoot);
  }

}
