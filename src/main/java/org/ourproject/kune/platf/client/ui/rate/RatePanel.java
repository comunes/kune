/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 *
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */
package org.ourproject.kune.platf.client.ui.rate;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class RatePanel extends Composite implements RateView {
    private Grid rateGrid;
    private Image[] starImg;
    private Label rateDesc;

    public RatePanel(final Double rate, final Integer byUsers) {
        initialize();
        layout();
        setProperties();
        if (rate != null) {
            setRate(rate);
        }
        if (byUsers != null) {
            setByUsers(byUsers);
        }
    }

    public void setByUsers(final Integer byUsers) {
        if (byUsers.intValue() == 0) {
            // i18n
            rateDesc.setText("(Not rated)");
        } else if (byUsers.intValue() == 1) {
            // i18n
            rateDesc.setText("(" + byUsers + " " + "user)");
        } else {
            // i18n
            rateDesc.setText("(" + byUsers + " " + "users)");
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
        rateDesc.addStyleName("kune-Margin-Medium-lr");
    }

    private void setRate(final Star stars[]) {
        for (int i = 0; i < 5; i++) {
            stars[i].getImage().applyTo(starImg[i]);
        }
    }

}
