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

import org.ourproject.kune.platf.client.ui.BasicThumb;
import org.ourproject.kune.workspace.client.cnt.FoldableContentPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;

public class GalleryFolderContentPanel extends FoldableContentPanel implements GalleryFolderContentView {

    private static final int TEXT_MAX_LENGHT = 15;
    private FlowPanel flowPanel;
    private final StateManager stateManager;
    private final Session session;

    public GalleryFolderContentPanel(final WorkspaceSkeleton ws, final I18nTranslationService i18n,
            final StateManager stateManager, final Session session) {
        super(ws, i18n);
        this.stateManager = stateManager;
        this.session = session;
    }

    public void addThumb(final StateToken token, final String title, final String imgUrl) {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            public void execute() {
                final BasicThumb thumb = new BasicThumb(imgUrl, session.getImgCropsize(), title, TEXT_MAX_LENGHT, true,
                        new ClickHandler() {
                            public void onClick(final ClickEvent event) {
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
