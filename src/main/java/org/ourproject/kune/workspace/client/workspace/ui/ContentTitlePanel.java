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

import org.ourproject.kune.workspace.client.workspace.ContentTitlePresenter;
import org.ourproject.kune.workspace.client.workspace.ContentTitleView;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ContentTitlePanel extends HorizontalPanel implements ContentTitleView {
    private final Label titleLabel;
    private final Label dateLabel;

    public ContentTitlePanel(final ContentTitlePresenter presenter) {
        titleLabel = new Label();
        HorizontalPanel rigthHP = new HorizontalPanel();
        dateLabel = new Label();

        add(titleLabel);
        add(rigthHP);
        rigthHP.add(dateLabel);

        setWidth("100%");
        titleLabel.addStyleName("kune-Margin-Large-l");
        titleLabel.addStyleName("kune-ft17px");
        dateLabel.addStyleName("kune-Margin-Large-r");
        dateLabel.addStyleName("kune-ft12px");
        titleLabel.addStyleName("kune-ContentTitleBar-l");
        rigthHP.addStyleName("kune-ContentTitleBar-r");
        setCellVerticalAlignment(titleLabel, VerticalPanel.ALIGN_MIDDLE);
        setCellVerticalAlignment(rigthHP, VerticalPanel.ALIGN_MIDDLE);
        rigthHP.setCellVerticalAlignment(dateLabel, VerticalPanel.ALIGN_MIDDLE);
    }

    public void setContentTitle(final String title) {
        titleLabel.setText(title);
    }

    public void setContentDate(final String date) {
        dateLabel.setText(date);
    }

    public void setColors(final String background, final String textColor) {
        DOM.setStyleAttribute(this.getElement(), "backgroundColor", background);
        DOM.setStyleAttribute(titleLabel.getElement(), "color", textColor);
        DOM.setStyleAttribute(dateLabel.getElement(), "color", textColor);
    }

    public void setDateVisible(final boolean visible) {
        dateLabel.setVisible(visible);

    }
}
