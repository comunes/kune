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
package cc.kune.core.client.sitebar.logo;

import cc.kune.core.client.sitebar.logo.SiteLogoPresenter.SiteLogoView;
import cc.kune.gspace.client.WsArmor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class SiteLogoViewImpl extends ViewWithUiHandlers<SiteLogoUiHandlers> implements SiteLogoView {

    interface SiteLogoViewImplUiBinder extends UiBinder<Widget, SiteLogoViewImpl> {
    }

    private static SiteLogoViewImplUiBinder uiBinder = GWT.create(SiteLogoViewImplUiBinder.class);

    @UiField
    Image logo;

    @Inject
    public SiteLogoViewImpl(final WsArmor armor) {
        armor.getSitebar().insert(uiBinder.createAndBindUi(this), 0);
    }

    @Override
    public Widget asWidget() {
        return logo;
    }

    @UiHandler("logo")
    void onLogoClick(final ClickEvent event) {
        if (getUiHandlers() != null) {
            getUiHandlers().onClick();
        }
    }

    @Override
    public void setSiteLogoUrl(final String siteLogoUrl) {
        logo.setUrl(siteLogoUrl);
    }
}
