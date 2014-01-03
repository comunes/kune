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
package cc.kune.docs.client;

import cc.kune.core.shared.SessionConstants;
import cc.kune.docs.client.actions.DocsClientActions;
import cc.kune.docs.shared.DocsToolConstants;
import cc.kune.gspace.client.tool.ContentViewerSelector;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class DocsParts.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class DocsParts {

  /**
   * Instantiates a new docs parts.
   * 
   * @param session
   *          the session
   * @param clientTool
   *          the client tool
   * @param viewerSelector
   *          the viewer selector
   * @param docsActions
   *          the docs actions
   * @param contentViewer
   *          the content viewer
   * @param folderViewer
   *          the folder viewer
   */
  @Inject
  public DocsParts(final SessionConstants session, final Provider<DocsClientTool> clientTool,
      final ContentViewerSelector viewerSelector, final DocsClientActions docsActions,
      final ContentViewerPresenter contentViewer, final FolderViewerPresenter folderViewer) {
    clientTool.get();
    viewerSelector.register(contentViewer, true, DocsToolConstants.TYPE_DOCUMENT);
    viewerSelector.register(folderViewer, true, DocsToolConstants.TYPE_ROOT,
        DocsToolConstants.TYPE_FOLDER);
  }
}
