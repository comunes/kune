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

import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.SiteToken;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Hyperlink;

public class SiteSignInLinkPanel implements SiteSignInLinkView {

    public static final String SITE_SIGN_IN = "kune-ssilp-hy";

    private final Hyperlink signInHyperlink;

    public SiteSignInLinkPanel(final SiteSignInLinkPresenter presenter, final I18nUITranslationService i18n,
            final WorkspaceSkeleton ws) {
        signInHyperlink = new Hyperlink();
        signInHyperlink.ensureDebugId(SITE_SIGN_IN);
        signInHyperlink.setText(i18n.t("Sign in to collaborate"));
        signInHyperlink.setTargetHistoryToken(SiteToken.signin.toString());
        ws.getSiteBar().add(signInHyperlink);
        ws.getSiteBar().addSpacer();
    }

    public void setVisible(final boolean visible) {
        signInHyperlink.setVisible(visible);
    }
}
