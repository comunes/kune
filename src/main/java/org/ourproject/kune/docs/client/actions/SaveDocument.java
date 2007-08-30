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

package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.docs.client.ui.cnt.DocumentContent;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.sitebar.client.Site;
import org.ourproject.kune.workspace.client.actions.WorkspaceAction;
import org.ourproject.kune.workspace.client.dto.StateDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class SaveDocument extends WorkspaceAction {
    private static final String NAME = "docs.SaveDocument";

    public SaveDocument() {
	super(NAME, DocsEvents.SAVE_DOCUMENT);
    }

    public void execute(final Object value, final Object extra) {
	save((StateDTO) value, (DocumentContent) extra);
    }

    private void save(final StateDTO content, final DocumentContent documentContent) {
	// i18n: Saving
	Site.showProgress("Saving");
	ContentServiceAsync server = ContentService.App.getInstance();
	server.save(user, content.getDocumentId(), content.getContent(), new AsyncCallback() {
	    public void onFailure(final Throwable caught) {
		// i18n: Error saving
		Site.hideProgress();
		Site.error("Error saving document");
		documentContent.onSaveFailed();
	    }

	    public void onSuccess(final Object result) {
		// i18n: Saved
		Site.hideProgress();
		Site.info("Document Saved");
		documentContent.onSaved();
	    }

	});
    }

}
