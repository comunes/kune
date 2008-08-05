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
package org.ourproject.kune.docs.client.actions;

import org.ourproject.kune.platf.client.dispatch.Action;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.Site;

public class ContentSetLanguageAction implements Action<String> {

    private final Session session;

    public ContentSetLanguageAction(final Session session) {
	this.session = session;
    }

    public void execute(final String value) {
	onContentSetLanguage(value);
    }

    private void onContentSetLanguage(final String languageCode) {
	Site.showProgressProcessing();
	final ContentServiceAsync server = ContentService.App.getInstance();
	final StateDTO currentState = session.getCurrentState();
	server.setLanguage(session.getUserHash(), currentState.getGroup().getShortName(), currentState.getDocumentId(),
		languageCode, new AsyncCallbackSimple<I18nLanguageDTO>() {
		    public void onSuccess(final I18nLanguageDTO lang) {
			Site.hideProgress();
			// FIXME
			// workspace.getContentSubTitleComponent().setContentLanguage(lang.getEnglishName());
		    }
		});
    }
}
