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
package cc.kune.docs.server;

import static cc.kune.docs.shared.DocsToolConstants.*;

import java.util.Arrays;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.server.AbstractWaveBasedServerTool;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.manager.ToolConfigurationManager;
import cc.kune.core.server.tool.ServerToolTarget;
import cc.kune.docs.shared.DocsToolConstants;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class DocumentServerTool.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class DocumentServerTool extends AbstractWaveBasedServerTool {

  /**
   * Instantiates a new document server tool.
   * 
   * @param contentManager
   *          the content manager
   * @param containerManager
   *          the container manager
   * @param configurationManager
   *          the configuration manager
   * @param i18n
   *          the i18n
   * @param creationService
   *          the creation service
   */
  @Inject
  public DocumentServerTool(final ContentManager contentManager,
      final ContainerManager containerManager, final ToolConfigurationManager configurationManager,
      final I18nTranslationService i18n, final CreationService creationService) {
    super(TOOL_NAME, ROOT_NAME, TYPE_ROOT, Arrays.asList(TYPE_DOCUMENT, TYPE_UPLOADEDFILE),
        Arrays.asList(TYPE_ROOT, TYPE_FOLDER), Arrays.asList(TYPE_FOLDER), Arrays.asList(TYPE_ROOT,
            TYPE_FOLDER), contentManager, containerManager, creationService, configurationManager, i18n,
        ServerToolTarget.forBoth);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.tool.ServerTool#initGroup(cc.kune.domain.User,
   * cc.kune.domain.Group, java.lang.Object[])
   */
  @Override
  public Group initGroup(final User user, final Group group, final Object... otherVars) {
    final Container rootFolder = createRoot(group);

    final boolean hasVars = otherVars.length >= 2;
    final String title = hasVars ? (String) otherVars[0] : i18n.t("Document sample");
    final String body = hasVars ? (String) otherVars[1] : i18n.t("This is only a sample of document");
    final String contentType = DocsToolConstants.TYPE_DOCUMENT;

    final Content content = createInitialContent(user, group, rootFolder, title, body, contentType);
    group.setDefaultContent(content);
    return group;
  }

}
