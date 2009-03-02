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
package org.ourproject.kune.workspace.client.sitebar.sitesign;

import org.ourproject.kune.platf.client.i18n.I18nUITranslationService;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SiteSignOutLinkPanel implements SiteSignOutLinkView {

    public static final String SITE_SIGN_OUT = "k-ssolp-lb";

    private final Label signOutLabel;

    public SiteSignOutLinkPanel(final SiteSignOutLinkPresenter presenter, final I18nUITranslationService i18n,
            final WorkspaceSkeleton ws) {
        signOutLabel = new Label();
        signOutLabel.setText(i18n.t("Sign out"));
        signOutLabel.addStyleName("k-sitebar-labellink");
        signOutLabel.ensureDebugId(SITE_SIGN_OUT);
        ws.getSiteBar().add(signOutLabel);
        signOutLabel.addClickListener(new ClickListener() {
            public void onClick(final Widget arg0) {
                NotifyUser.showProgressProcessing();
                presenter.doSignOut();
            }
        });
    }

    public void setVisible(final boolean visible) {
        signOutLabel.setVisible(visible);
    }
}
