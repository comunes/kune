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

import org.ourproject.kune.workspace.client.workspace.ContentSubTitlePresenter;
import org.ourproject.kune.workspace.client.workspace.ContentSubTitleView;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class ContentSubTitlePanel extends HorizontalPanel implements ContentSubTitleView {
    private final Label titleLabel;
    private final Label languageLabel;

    public ContentSubTitlePanel(final ContentSubTitlePresenter presenter) {
	titleLabel = new Label();
	Label languageTitle = new Label();
	languageLabel = new Label();
	HTML expandPanel = new HTML("<b></b>");
	add(titleLabel);
	add(expandPanel);
	add(languageTitle);
	add(languageLabel);
	// i18n
	languageTitle.setText("Language: ");
	// setCellWidth(expandPanel, "100%");
	expandPanel.addStyleName("kune-expandHoriz");
	titleLabel.addStyleName("kune-Margin-Large-l");
	titleLabel.addStyleName("kune-ft15px");
	languageLabel.addStyleName("kune-Margin-Large-r");
	languageLabel.addStyleName("kune-ft12px");
    }

    public void setContentSubTitle(final String title) {
	titleLabel.setText(title);
    }

    public void setContentSubTitleLanguage(final String lang) {
	languageLabel.setText(lang);
    }

}
