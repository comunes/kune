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

package org.ourproject.kune.workspace.client.ui.newtmp.title;

import org.ourproject.kune.platf.client.ui.EditableClickListener;
import org.ourproject.kune.platf.client.ui.EditableIconLabel;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.TitleBar;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;

import com.google.gwt.user.client.ui.Label;

public class EntityTitlePanel implements EntityTitleView {
    private final EditableIconLabel titleLabel;
    private final Label dateLabel;

    public EntityTitlePanel(final WorkspaceSkeleton ws, final EntityTitlePresenter presenter) {
	titleLabel = new EditableIconLabel(new EditableClickListener() {
	    public void onEdited(final String text) {
		presenter.onTitleRename(text);
	    }
	});
	dateLabel = new Label();

	final TitleBar wsTitle = ws.getEntityWorkspace().getTitle();
	wsTitle.add(titleLabel);
	wsTitle.addFill();
	wsTitle.add(dateLabel);
	titleLabel.addStyleName("kune-Margin-Large-l");
	titleLabel.addStyleName("kune-ft17px");
	dateLabel.addStyleName("kune-Margin-Large-r");
	dateLabel.addStyleName("kune-ft12px");
	titleLabel.addStyleName("kune-ContentTitleBar-l");
	dateLabel.addStyleName("kune-ContentTitleBar-r");
    }

    public void restoreOldTitle() {
	titleLabel.restoreOldText();
    }

    public void setContentDate(final String date) {
	dateLabel.setText(date);
    }

    public void setContentTitle(final String title) {
	titleLabel.setText(title);
    }

    public void setContentTitleEditable(final boolean editable) {
	titleLabel.setEditable(editable);
    }

    public void setDateVisible(final boolean visible) {
	dateLabel.setVisible(visible);

    }

    public void setTheme(final WsTheme theme) {

    }
}
