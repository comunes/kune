/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.docs.client.cnt.folder.viewer;

import org.ourproject.kune.workspace.client.cnt.AbstractContentPanel;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Label;

public class DocFolderContentPanel extends AbstractContentPanel implements DocFolderContentView {

    public DocFolderContentPanel(WorkspaceSkeleton ws) {
        super(ws);
        Label label = new Label("Folder properties, translations ..." + Site.IN_DEVELOPMENT);
        label.setStyleName("kune-Content-Main");
        label.addStyleName("kune-Margin-7-trbl");
        initWidget(label);
    }
}
