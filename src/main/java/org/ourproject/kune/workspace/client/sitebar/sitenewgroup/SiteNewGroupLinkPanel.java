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
package org.ourproject.kune.workspace.client.sitebar.sitenewgroup;

import org.ourproject.kune.workspace.client.site.SiteToken;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import cc.kune.core.client.i18n.I18nUITranslationService;

import com.google.gwt.user.client.ui.Hyperlink;

public class SiteNewGroupLinkPanel implements SiteNewGroupLinkView {

    public SiteNewGroupLinkPanel(final SiteNewGroupLinkPresenter presenter, final WorkspaceSkeleton ws,
            final I18nUITranslationService i18n) {
        final Hyperlink newGroupHyperlink = new Hyperlink();
        newGroupHyperlink.setText(i18n.t("Create New Group"));
        newGroupHyperlink.setTargetHistoryToken(SiteToken.newgroup.toString());
        ws.getSiteBar().addSeparator();
        ws.getSiteBar().add(newGroupHyperlink);
        ws.getSiteBar().addSpacer();
    }
}
