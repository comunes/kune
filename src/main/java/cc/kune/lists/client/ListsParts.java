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
package cc.kune.lists.client;

import cc.kune.core.shared.SessionConstants;
import cc.kune.gspace.client.tool.ContentViewerSelector;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;
import cc.kune.lists.client.actions.ListsClientActions;
import cc.kune.lists.shared.ListsToolConstants;

import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class ListsParts.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ListsParts {

  /**
   * Instantiates a new lists parts.
   * 
   * @param session
   *          the session
   * @param clientTool
   *          the client tool
   * @param viewerSelector
   *          the viewer selector
   * @param listsActions
   *          the lists actions
   * @param contentViewer
   *          the content viewer
   * @param folderViewer
   *          the folder viewer
   */
  @Inject
  public ListsParts(final SessionConstants session, final Provider<ListsClientTool> clientTool,
      final ContentViewerSelector viewerSelector, final ListsClientActions listsActions,
      final ContentViewerPresenter contentViewer, final FolderViewerPresenter folderViewer) {
    clientTool.get();
    viewerSelector.register(contentViewer, true, ListsToolConstants.TYPE_POST);
    viewerSelector.register(folderViewer, true, ListsToolConstants.TYPE_ROOT,
        ListsToolConstants.TYPE_LIST);
  }
}
