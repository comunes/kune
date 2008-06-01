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

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.workspace.client.workspace.ContentSubTitlePresenter;
import org.ourproject.kune.workspace.client.workspace.ContentSubTitleView;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ContentSubTitlePanel extends HorizontalPanel implements ContentSubTitleView {
    private final Label subTitleLeftLabel;
    private final Label subTitleRightLabel;

    public ContentSubTitlePanel(final ContentSubTitlePresenter presenter, final I18nTranslationService i18n) {

        subTitleLeftLabel = new Label();
        HorizontalPanel rigthHP = new HorizontalPanel();
        subTitleRightLabel = new Label();

        add(subTitleLeftLabel);
        add(rigthHP);
        rigthHP.add(subTitleRightLabel);

        setWidth("100%");
        subTitleRightLabel.setText(i18n.t("Language:"));
        subTitleLeftLabel.addStyleName("kune-Margin-Large-l");
        subTitleLeftLabel.addStyleName("kune-ft15px");
        subTitleLeftLabel.addStyleName("kune-ContentSubTitleBar-l");
        subTitleRightLabel.addStyleName("kune-Margin-Large-r");
        subTitleRightLabel.addStyleName("kune-ft12px");
        rigthHP.addStyleName("kune-ContentSubTitleBar-r");
        setCellVerticalAlignment(rigthHP, VerticalPanel.ALIGN_MIDDLE);
        rigthHP.setCellVerticalAlignment(subTitleLeftLabel, VerticalPanel.ALIGN_MIDDLE);
        rigthHP.setCellVerticalAlignment(subTitleRightLabel, VerticalPanel.ALIGN_MIDDLE);
    }

    public void setColors(final String background, final String textColor) {
        DOM.setStyleAttribute(this.getElement(), "backgroundColor", background);
        DOM.setStyleAttribute(subTitleLeftLabel.getElement(), "color", textColor);
        DOM.setStyleAttribute(subTitleRightLabel.getElement(), "color", textColor);
    }

    public void setContentSubTitleLeft(final String subTitle) {
        subTitleLeftLabel.setText(subTitle);
    }

    public void setContentSubTitleLeftVisible(final boolean visible) {
        subTitleLeftLabel.setVisible(visible);
    }

    public void setContentSubTitleRight(final String subTitle) {
        subTitleRightLabel.setText(subTitle);

    }

    public void setContentSubTitleRightVisible(final boolean visible) {
        subTitleRightLabel.setVisible(visible);
    }

}
