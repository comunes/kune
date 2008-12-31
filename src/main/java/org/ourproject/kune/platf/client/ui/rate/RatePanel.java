/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.ui.rate;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.workspace.client.skel.Toolbar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class RatePanel extends Composite implements RateView {
    private Grid rateGrid;
    private Image[] starImg;
    private Label rateDesc;
    private final I18nTranslationService i18n;

    public RatePanel(final Double rate, final Integer byUsers, final I18nTranslationService i18n,
            final WorkspaceSkeleton ws) {
        this.i18n = i18n;
        initialize();
        layout();
        setProperties();
        if (rate != null) {
            setRate(rate);
        }
        if (byUsers != null) {
            setByUsers(byUsers);
        }
        final Toolbar bottomBar = ws.getEntityWorkspace().getContentBottomBar();
        bottomBar.addFill();
        bottomBar.add(this);
    }

    public void setByUsers(final Integer byUsers) {
        if (byUsers.intValue() == 0) {
            rateDesc.setText(i18n.t("(Not rated)"));
        } else if (byUsers.intValue() == 1) {
            // i18n params pluralization
            rateDesc.setText(i18n.t("([%d] user)", byUsers));
        } else {
            rateDesc.setText(i18n.t("([%d] users)", byUsers));
        }
    }

    public void setRate(final Double rate) {
        setRate(Star.genStars(rate.doubleValue()));
    }

    private void initialize() {
        rateGrid = new Grid(1, 6);
        starImg = new Image[5];
        for (int i = 0; i < 5; i++) {
            starImg[i] = new Image();
            new Star(Star.GREY).getImage().applyTo(starImg[i]);
        }
        rateDesc = new Label();
    }

    private void layout() {
        initWidget(rateGrid);
        for (int i = 0; i < 5; i++) {
            rateGrid.setWidget(0, i, starImg[i]);
        }
        rateGrid.setWidget(0, 5, rateDesc);
    }

    private void setProperties() {
        rateGrid.setCellPadding(0);
        rateGrid.setCellSpacing(0);
        rateGrid.setBorderWidth(0);
        rateGrid.addStyleName("kune-RatePanel-Stars");
        rateDesc.addStyleName("kune-RatePanel-Label");
        rateDesc.addStyleName("kune-Margin-Small-lr");
    }

    private void setRate(final Star stars[]) {
        for (int i = 0; i < 5; i++) {
            stars[i].getImage().applyTo(starImg[i]);
        }
    }

}
