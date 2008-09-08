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

package org.ourproject.kune.workspace.client.title;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.workspace.client.skel.TitleBar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Label;

public class EntitySubTitlePanel implements EntitySubTitleView {
    private final Label subTitleLeftLabel;
    private final Label subTitleRightLabel;

    public EntitySubTitlePanel(final EntitySubTitlePresenter presenter, final I18nTranslationService i18n,
	    final WorkspaceSkeleton ws) {
	subTitleLeftLabel = new Label();
	subTitleRightLabel = new Label();

	final TitleBar wsSubTitle = ws.getEntityWorkspace().getSubTitle();
	wsSubTitle.add(subTitleLeftLabel);
	wsSubTitle.addFill();
	wsSubTitle.add(subTitleRightLabel);

	subTitleRightLabel.setText(i18n.t("Language:"));

	subTitleLeftLabel.addStyleName("kune-Margin-Large-l");
	subTitleLeftLabel.addStyleName("kune-ft15px");
	subTitleRightLabel.addStyleName("kune-Margin-Large-r");
	subTitleRightLabel.addStyleName("kune-ft12px");
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
