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
package org.ourproject.kune.workspace.client.sitebar.siteprogress;

import org.ourproject.kune.workspace.client.sitebar.sitepublic.SitePublicSpaceLink;

import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class SiteProgressPanel implements SiteProgressView {
    private static final int MAX_TIME_PROGRESS_BAR = 20000;

    private final Widget progressPanel;
    private final Widget progressText;
    private Timer timeProgressMaxTime;

    private final Provider<SitePublicSpaceLink> publicLinkProvider;

    public SiteProgressPanel(final SiteProgressPresenter presenter,
            final Provider<SitePublicSpaceLink> publicLinkProvider) {
        this.publicLinkProvider = publicLinkProvider;
        progressPanel = RootPanel.get("kuneprogresspanel");
        progressText = RootPanel.get("kuneprogresstext");
    }

    public void hideProgress() {
        timeProgressMaxTime.cancel();
        progressPanel.setVisible(false);
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                publicLinkProvider.get().setVisible(true);
            }
        });
    }

    public void showProgress(final String text) {
        if (timeProgressMaxTime == null) {
            timeProgressMaxTime = new Timer() {
                @Override
                public void run() {
                    hideProgress();
                }
            };
        }
        timeProgressMaxTime.schedule(MAX_TIME_PROGRESS_BAR);
        publicLinkProvider.get().setVisible(false);
        progressPanel.setVisible(true);
        DOM.setInnerText(progressText.getElement(), text);
    }
}
