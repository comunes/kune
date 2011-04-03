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
package org.ourproject.kune.gallery.client.cnt;

import cc.kune.common.client.ui.BasicThumb;
import cc.kune.core.client.cnt.FoldableContentPanel;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.WsArmor;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;

public class GalleryFolderContentPanel extends FoldableContentPanel implements GalleryFolderContentView {

    private static final int TEXT_MAX_LENGHT = 15;
    private FlowPanel flowPanel;
    private final Session session;
    private final StateManager stateManager;

    @Inject
    public GalleryFolderContentPanel(final WsArmor ws, final I18nTranslationService i18n, final CoreResources res,
            final StateManager stateManager, final Session session) {
        super(ws, i18n, res);
        this.stateManager = stateManager;
        this.session = session;
    }

    @Override
    public void addThumb(final StateToken token, final String title, final String imgUrl) {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                final BasicThumb thumb = new BasicThumb(imgUrl, session.getImgCropsize(), title, TEXT_MAX_LENGHT, true,
                        new ClickHandler() {
                            @Override
                            public void onClick(final ClickEvent event) {
                                stateManager.gotoStateToken(token);
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

    @Override
    public void setThumbPanel() {
        if (flowPanel == null) {
            flowPanel = new FlowPanel();
        } else {
            flowPanel.clear();
        }
        super.setWidgetAsContent(flowPanel, true);
    }
}
