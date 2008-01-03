/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.app.ui;

import org.ourproject.kune.platf.client.app.DesktopView;
import org.ourproject.kune.platf.client.state.Session1;
import org.ourproject.kune.sitebar.client.SiteBarFactory;
import org.ourproject.kune.sitebar.client.bar.SiteBar;
import org.ourproject.kune.sitebar.client.bar.SiteBarListener;
import org.ourproject.kune.sitebar.client.msg.SiteMessage;
import org.ourproject.kune.workspace.client.workspace.Workspace;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.QuickTips;
import com.gwtext.client.widgets.QuickTipsConfig;

public class DesktopPanel extends AbsolutePanel implements DesktopView {
    public DesktopPanel(final Workspace workspace, final SiteBarListener listener, final Session1 session) {
        QuickTips.init(); // extgwt tips
        new QuickTipsConfig() {
            {
                setMinWidth(100);
                setShowDelay(300);
                setInterceptTitles(true);
            }
        };
        SiteBar siteBar = SiteBarFactory.createSiteBar(listener, session);
        SiteMessage siteMessage = SiteBarFactory.getSiteMessage();
        this.add((Widget) siteMessage.getView(), Window.getClientWidth() * 20 / 100 - 10, 2);
        this.setSize("100%", "100%");
        this.add((Widget) siteBar.getView(), 0, 0);
        this.add((Widget) workspace.getView(), 0, 20);
        this.addStyleName("kunebody");
        initResizeListener(this, workspace, siteMessage);
    }

    public void attach() {
        RootPanel.get().add(this);
    }

    private void initResizeListener(final AbsolutePanel desktop, final Workspace workspace,
            final SiteMessage siteMessage) {
        Window.addWindowResizeListener(new WindowResizeListener() {
            public void onWindowResized(final int width, final int height) {
                workspace.adjustSize(width, height);
                siteMessage.adjustWidth(width);
                desktop.setWidgetPosition((Widget) siteMessage.getView(), Window.getClientWidth() * 20 / 100 - 10, 2);
            }
        });
        Window.enableScrolling(false);
    }
}
