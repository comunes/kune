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

package org.ourproject.kune.docs.client.cnt.reader.ui;

import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderControlPresenter;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderControlView;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.Toolbar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class DocumentReaderControlPanel implements DocumentReaderControlView {
    private final ToolbarButton editBtn;
    private final ToolbarButton deleteBtn;
    private final ToolbarButton translateBtn;
    private final Widget space1;
    private final Widget space2;

    public DocumentReaderControlPanel(final DocumentReaderControlPresenter presenter,
	    final I18nUITranslationService i18n, final WorkspaceSkeleton ws) {
	editBtn = new ToolbarButton(i18n.tWithNT("Edit", "used in button"), new ButtonListenerAdapter() {
	    public void onClick(final Button button, final EventObject e) {
		presenter.onEditClicked();
	    }
	});

	deleteBtn = new ToolbarButton(i18n.tWithNT("Delete", "used in button"), new ButtonListenerAdapter() {
	    public void onClick(final Button button, final EventObject e) {
		presenter.onDeleteClicked();
	    }
	});

	translateBtn = new ToolbarButton(i18n.tWithNT("Translate", "used in button"), new ButtonListenerAdapter() {
	    public void onClick(final Button button, final EventObject e) {
		presenter.onTranslateClicked();
		ws.showAlertMessage(i18n.t("Alert"), i18n.t("Sorry, this functionality is currently in development"));
	    }
	});

	final Toolbar contentTopBar = ws.getEntityWorkspace().getContentTopBar();
	contentTopBar.add(editBtn);
	space1 = contentTopBar.addSpacer();
	contentTopBar.add(deleteBtn);
	space2 = contentTopBar.addSpacer();
	contentTopBar.add(translateBtn);
	setEditEnabled(false);
	setDeleteEnabled(false);
	setTranslateEnabled(false);
	hide();
    }

    public void hide() {
	this.setVisible(false);
    }

    public void setDeleteEnabled(final boolean isEnabled) {
	deleteBtn.setVisible(isEnabled);
    }

    public void setEditEnabled(final boolean isEnabled) {
	editBtn.setVisible(isEnabled);
    }

    public void setTranslateEnabled(final boolean isEnabled) {
	translateBtn.setVisible(isEnabled);
    }

    public void show() {
	this.setVisible(true);
    }

    private void setVisible(final boolean visible) {
	editBtn.setVisible(visible);
	deleteBtn.setVisible(visible);
	translateBtn.setVisible(visible);
	space1.setVisible(visible);
	space2.setVisible(visible);
    }
}
