/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.platf.client.ui.rate.RatePanel;
import org.ourproject.kune.workspace.client.workspace.ContentSubTitlePresenter;
import org.ourproject.kune.workspace.client.workspace.ContentSubTitleView;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ContentSubTitlePanel extends HorizontalPanel implements ContentSubTitleView {
    private final Label titleLabel;
    private final RatePanel ratePanel;
    private final HTML expandPanel;

    public ContentSubTitlePanel(final ContentSubTitlePresenter presenter) {
	titleLabel = new Label();
	ratePanel = new RatePanel(null, null);
	expandPanel = new HTML("<b></b>");
	add(titleLabel);
	add(ratePanel);
	add(expandPanel);
	setWidth("100%");
	setCellWidth(expandPanel, "100%");
	addStyleName("kune-ContentSubTitleBar");
	expandPanel.addStyleName("kune-expandHoriz");
	setCellVerticalAlignment(titleLabel, VerticalPanel.ALIGN_MIDDLE);
	setCellVerticalAlignment(ratePanel, VerticalPanel.ALIGN_MIDDLE);
    }

    public void setContentSubTitle(final String title, final Double rate, final Integer rateByUsers) {
	titleLabel.setText(title);
	ratePanel.setRate(rate);
	ratePanel.setByUsers(rateByUsers);
    }
}
