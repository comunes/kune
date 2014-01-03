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

import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.core.server.tool.ServerToolWithWave;
import cc.kune.domain.Container;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractWaveBasedServerTool.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractWaveBasedServerTool extends AbstractServerTool implements
    ServerToolWithWave {

  /**
   * Instantiates a new abstract wave based server tool.
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
  public AbstractWaveBasedServerTool(final String name, final String rootName, final String typeRoot,
      final List<String> validContents, final List<String> validContentParents,
      final List<String> validContainers, final List<String> validContainerParents,
      final ContentManager contentManager, final ContainerManager containerManager,
      final CreationService creationService, final ToolConfigurationManager configurationManager,
      final I18nTranslationService i18n, final ServerToolTarget target) {
    super(name, rootName, typeRoot, validContents, validContentParents, validContainers,
        validContainerParents, contentManager, containerManager, creationService, configurationManager,
        i18n, target);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.tool.ServerToolWithWave#getNewContentAdditionalParts
   * (cc.kune.domain.Container)
   */
  @Override
  public String[] getNewContentAdditionalParts(final Container containerParent) {
    return ArrayUtils.EMPTY_STRING_ARRAY;
  }

}
