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
 \*/
package org.ourproject.kune.workspace.client.skel;

import org.ourproject.kune.platf.client.ui.AbstractToolbar;
import org.ourproject.kune.platf.client.ui.SimpleToolbar;
import org.ourproject.kune.workspace.client.themes.WsTheme;

import com.calclab.suco.client.events.Listener0;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.event.ContainerListenerAdapter;
import com.gwtext.client.widgets.layout.AnchorLayout;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;

public class WorkspaceSkeleton {
    public static final int DEF_BAR_HEIGHT = 26;
    private final Entity entity;
    private final SimpleToolbar sitebar;
    private final ExtElement extRootBody;

    public WorkspaceSkeleton() {
        extRootBody = new ExtElement(RootPanel.getBodyElement());

        final Panel container = new Panel();
        container.setLayout(new FitLayout());
        container.setBorder(false);
        container.setPaddings(5);
        container.addStyleName("k-transp");

        final Panel mainPanel = new Panel();
        mainPanel.setLayout(new AnchorLayout());
        mainPanel.setBorder(false);
        mainPanel.addStyleName("k-transp");

        sitebar = new SimpleToolbar();
        sitebar.setStyleName("k-sitebar");
        sitebar.addStyleName("k-transp");
        sitebar.setHeight(String.valueOf(DEF_BAR_HEIGHT));

        entity = new Entity();

        mainPanel.add(sitebar, new AnchorLayoutData("100%"));
        mainPanel.add(entity.getPanel(), new AnchorLayoutData("100% -" + DEF_BAR_HEIGHT));
        container.add(mainPanel);
        new Viewport(container);
    }

    public void addInSummary(final Panel panel) {
        entity.addInSummary(panel);
    }

    public void addInTools(final Widget widget) {
        entity.addInTools(widget);
    }

    public void addListenerInEntitySummary(final ContainerListenerAdapter listener) {
        entity.addListenerInEntitySummary(listener);
    }

    public void addToEntityMainHeader(final Widget widget) {
        entity.addToEntityMainHeader(widget);
    }

    public void askConfirmation(final String title, final String message, final Listener0 onConfirmed,
            final Listener0 onCancel) {
        MessageBox.confirm(title, message, new MessageBox.ConfirmCallback() {
            public void execute(final String btnID) {
                if ("yes".equals(btnID)) {
                    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                        public void execute() {
                            onConfirmed.onEvent();
                        }
                    });
                } else {
                    onCancel.onEvent();
                }
            }
        });
    }

    public EntityWorkspace getEntityWorkspace() {
        return entity.getEntityWorkspace();
    }

    public AbstractToolbar getSiteBar() {
        return sitebar;
    }

    public Toolbar getSiteTraybar() {
        return entity.getSiteTraybar();
    }

    public void mask() {
        extRootBody.mask();
    }

    public void mask(final String message) {
        extRootBody.mask(message, "x-mask-loading");
    }

    public void promptMessage(final String title, final String message, final Listener0 onEnter) {
        MessageBox.prompt(title, message, new MessageBox.PromptCallback() {
            public void execute(final String btnID, final String text) {
                // FIXME: use btnID
                onEnter.onEvent();
            }
        });
    }

    public void refreshSummary() {
        entity.refreshSummary();
    }

    public void setMaximized(final boolean maximized) {
        entity.setMaximized(maximized);
    }

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
        entity.setTheme(oldTheme, newTheme);
    }

    public void showAlertMessage(final String title, final String message) {
        MessageBox.alert(title, message, new MessageBox.AlertCallback() {
            public void execute() {
                // Do nothing
            }
        });
    }

    public void unMask() {
        extRootBody.unmask();
    }
}
