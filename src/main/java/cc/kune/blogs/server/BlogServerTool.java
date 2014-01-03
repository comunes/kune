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
package cc.kune.blogs.server;

import static cc.kune.blogs.shared.BlogsToolConstants.*;

import java.util.Arrays;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.server.AbstractWaveBasedServerTool;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class BlogServerTool.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BlogServerTool extends AbstractWaveBasedServerTool {

  /**
   * Instantiates a new blog server tool.
   *
   * @param contentManager the content manager
   * @param containerManager the container manager
   * @param configurationManager the configuration manager
   * @param i18n the i18n
   * @param creationService the creation service
   */
  @Inject
  public BlogServerTool(final ContentManager contentManager, final ContainerManager containerManager,
      final ToolConfigurationManager configurationManager, final I18nTranslationService i18n,
      final CreationService creationService) {
    super(TOOL_NAME, ROOT_NAME, TYPE_ROOT, Arrays.asList(TYPE_POST, TYPE_UPLOADEDFILE),
        Arrays.asList(TYPE_BLOG), Arrays.asList(TYPE_BLOG), Arrays.asList(TYPE_ROOT), contentManager,
        containerManager, creationService, configurationManager, i18n, ServerToolTarget.forBoth);
  }

  /* (non-Javadoc)
   * @see cc.kune.core.server.tool.ServerTool#initGroup(cc.kune.domain.User, cc.kune.domain.Group, java.lang.Object[])
   */
  @Override
  public Group initGroup(final User user, final Group group, final Object... otherVars) {
    createRoot(group);
    return group;
  }
}
