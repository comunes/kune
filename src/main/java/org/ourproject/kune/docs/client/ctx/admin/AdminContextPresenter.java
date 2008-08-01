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

package org.ourproject.kune.docs.client.ctx.admin;

import java.util.Date;
import java.util.List;

import org.ourproject.kune.docs.client.actions.DocsEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.TagResultDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.workspace.TagsSummary;

import com.calclab.suco.client.container.Provider;

public class AdminContextPresenter implements AdminContext {

    private AdminContextView view;
    private final Session session;
    private final Provider<TagsSummary> tagsSummaryProvider;

    public AdminContextPresenter(final Session session, final Provider<TagsSummary> tagsSummaryProvider) {
	this.session = session;
	this.tagsSummaryProvider = tagsSummaryProvider;
    }

    public void doChangeLanguage(final String langCode) {
	DefaultDispatcher.getInstance().fire(DocsEvents.SET_LANGUAGE, langCode);
    }

    public View getView() {
	return view;
    }

    public void init(final AdminContextView view) {
	this.view = view;
    }

    public void setPublishedOn(final Date date) {
	DefaultDispatcher.getInstance().fire(DocsEvents.SET_PUBLISHED_ON, date);
    }

    public void setState(final StateDTO content) {
	// In the future check the use of these components by each tool
	final I18nLanguageDTO language = content.getLanguage();
	final AccessListsDTO accessLists = content.getAccessLists();
	final Date publishedOn = content.getPublishedOn();
	final String tags = content.getTags();
	final List<UserSimpleDTO> authors = content.getAuthors();

	if (content.hasDocument()) {
	    if (tags != null) {
		view.setTags(tags);
	    } else {
		view.removeTagsComponent();
	    }
	    if (language != null) {
		view.setLanguage(language);
	    } else {
		view.removeLangComponent();
	    }
	    if (authors != null) {
		view.setAuthors(authors);
	    } else {
		view.removeAuthorsComponent();
	    }
	    if (publishedOn != null) {
		view.setPublishedOn(publishedOn);
	    } else {
		view.removePublishedOnComponent();
	    }
	    if (accessLists != null) {
		view.setAccessLists(accessLists);
	    } else {
		view.removeAccessListComponent();
	    }
	}
    }

    public void setTags(final String tagsString) {
	Site.showProgressProcessing();
	final ContentServiceAsync server = ContentService.App.getInstance();
	final StateDTO currentState = session.getCurrentState();
	server.setTags(session.getUserHash(), currentState.getGroup().getShortName(), currentState.getDocumentId(),
		tagsString, new AsyncCallbackSimple<List<TagResultDTO>>() {
		    public void onSuccess(final List<TagResultDTO> result) {
			tagsSummaryProvider.get().setGroupTags(result);
			Site.hideProgress();
		    }
		});
    }

}
