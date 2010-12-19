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
package cc.kune.core.client.sitebar.logo;

import cc.kune.core.ws.armor.client.WsArmor;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class SiteLogoViewImpl extends ViewImpl implements SiteLogoPresenter.SiteLogoView {

    private final Image siteLogoImg;

    @Inject
    public SiteLogoViewImpl(WsArmor armor) {
        siteLogoImg = new Image();
        siteLogoImg.addStyleName("k-floatright");
        armor.getSitebar().add(siteLogoImg);
    }

    @Override
    public void setSiteLogoUrl(final String siteLogoUrl) {
        siteLogoImg.setUrl(siteLogoUrl);
    }

    @Override
    public Widget asWidget() {
        return siteLogoImg;
    }
}
