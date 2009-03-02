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
package org.ourproject.kune.gallery.client.cnt;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.BasicThumb;
import org.ourproject.kune.workspace.client.cnt.FoldableContentPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class GalleryFolderContentPanel extends FoldableContentPanel implements GalleryFolderContentView {

    private static final int TEXT_MAX_LENGHT = 15;
    private FlowPanel flowPanel;
    private final StateManager stateManager;
    private final Session session;

    public GalleryFolderContentPanel(WorkspaceSkeleton ws, I18nTranslationService i18n, StateManager stateManager,
            Session session) {
        super(ws, i18n);
        this.stateManager = stateManager;
        this.session = session;
    }

    public void addThumb(final StateToken token, final String title, final String imgUrl) {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                BasicThumb thumb = new BasicThumb(imgUrl, session.getImgCropsize(), title, TEXT_MAX_LENGHT, true,
                        new ClickListener() {
                            public void onClick(Widget sender) {
                                stateManager.gotoToken(token);
                            }
                        });
                thumb.setHeight("100");
                thumb.setWidth("100");
                if (title.length() > TEXT_MAX_LENGHT) {
                    thumb.setTooltip(title);
                }
                flowPanel.add(thumb);
            }
        });
    }

    public void setThumbPanel() {
        if (flowPanel == null) {
            flowPanel = new FlowPanel();
        } else {
            flowPanel.clear();
        }
        super.setWidgetAsContent(flowPanel, true);
    }
}
