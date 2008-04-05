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

package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.platf.client.ui.CustomPushButton;
import org.ourproject.kune.platf.client.ui.rate.RateItPanel;
import org.ourproject.kune.platf.client.ui.rate.RateItPresenter;
import org.ourproject.kune.platf.client.ui.rate.RatePanel;
import org.ourproject.kune.workspace.client.workspace.ContentBottomToolBarPresenter;
import org.ourproject.kune.workspace.client.workspace.ContentBottomToolBarView;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ContentBottomToolBarPanel extends HorizontalPanel implements ContentBottomToolBarView {

    private final RatePanel rate;
    private final RateItPresenter rateItPresenter;
    private final RateItPanel rateIt;
    private CustomPushButton btn;

    public ContentBottomToolBarPanel(final ContentBottomToolBarPresenter presenter) {
        rate = new RatePanel(null, null);
        rateItPresenter = new RateItPresenter();
        rateIt = new RateItPanel(rateItPresenter);
        rateItPresenter.init(rateIt);
        Label expand = new Label("");
        this.add(rateIt);
        this.add(expand);
        this.add(rate);
        this.setWidth("100%");
        expand.setWidth("100%");
        this.setCellWidth(expand, "100%");
        this.addStyleName("kune-ContentToolBarPanel");
        this.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        this.setCellVerticalAlignment(rate, VerticalPanel.ALIGN_MIDDLE);
        this.setCellVerticalAlignment(rateIt, VerticalPanel.ALIGN_MIDDLE);
        rate.setVisible(false);
        rateIt.setVisible(false);
        // TODO setEnabled false to RateIt
    }

    public void setRate(final Double value, final Integer rateByUsers) {
        rate.setRate(value);
        rate.setByUsers(rateByUsers);
    }

    public void setRateIt(final Double currentUserRate) {
        rateItPresenter.setRate(currentUserRate);
    }

    public void setRateVisible(final boolean visible) {
        rate.setVisible(visible);
    }

    public void setRateItVisible(final boolean visible) {
        rateIt.setVisible(visible);
    }

    public void addButton(final String caption, final ClickListener listener) {
        btn = new CustomPushButton(caption, listener);
        this.insert(btn, 0);
        btn.addStyleName("kune-Button-Large-lrSpace");
    }

    public void setButtonVisible(final boolean isEnabled) {
        btn.setVisible(isEnabled);
    }

}
