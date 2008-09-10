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

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.TagResultDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.tags.TagsSummary;
import org.ourproject.kune.workspace.client.title.EntitySubTitle;
import org.ourproject.kune.workspace.client.title.EntityTitle;

import com.calclab.suco.client.provider.Provider;

public class AdminContextPresenter implements AdminContext {

    private AdminContextView view;
    private final Session session;
    private final Provider<TagsSummary> tagsSummaryProvider;
    private final Provider<ContentServiceAsync> contentServiceProvider;
    private final EntitySubTitle entitySubTitle;
    private final EntityTitle entityTitle;
    private final StateManager stateManager;

    public AdminContextPresenter(final Session session, final StateManager stateManager,
	    final Provider<TagsSummary> tagsSummaryProvider,
	    final Provider<ContentServiceAsync> contentServiceProvider, final EntityTitle entityTitle,
	    final EntitySubTitle entitySubTitle) {
	this.session = session;
	this.stateManager = stateManager;
	this.tagsSummaryProvider = tagsSummaryProvider;
	this.contentServiceProvider = contentServiceProvider;
	this.entityTitle = entityTitle;
	this.entitySubTitle = entitySubTitle;
    }

    public void addAuthor(final String authorShortName) {
	Site.showProgressProcessing();
	final StateDTO currentState = session.getCurrentState();
	contentServiceProvider.get().addAuthor(session.getUserHash(), currentState.getGroup().getShortName(),
		currentState.getDocumentId(), authorShortName, new AsyncCallbackSimple<Object>() {
		    public void onSuccess(final Object result) {
			Site.hideProgress();
			stateManager.reload();
		    }
		});
    }

    public void delAuthor(final String authorShortName) {
	Site.showProgressProcessing();
	final StateDTO currentState = session.getCurrentState();
	contentServiceProvider.get().removeAuthor(session.getUserHash(), currentState.getGroup().getShortName(),
		currentState.getDocumentId(), authorShortName, new AsyncCallbackSimple<Object>() {
		    public void onSuccess(final Object result) {
			Site.hideProgress();
			stateManager.reload();
		    }
		});
    }

    public void doChangeLanguage(final String langCode) {
	Site.showProgressProcessing();
	final StateDTO currentState = session.getCurrentState();
	contentServiceProvider.get().setLanguage(session.getUserHash(), currentState.getGroup().getShortName(),
		currentState.getDocumentId(), langCode, new AsyncCallbackSimple<I18nLanguageDTO>() {
		    public void onSuccess(final I18nLanguageDTO lang) {
			Site.hideProgress();
			entitySubTitle.setContentLanguage(lang.getEnglishName());
		    }
		});
    }

    public View getView() {
	return view;
    }

    public void init(final AdminContextView view) {
	this.view = view;
    }

    public void setPublishedOn(final Date publishedOn) {
	Site.showProgressProcessing();
	final StateDTO currentState = session.getCurrentState();
	contentServiceProvider.get().setPublishedOn(session.getUserHash(), currentState.getGroup().getShortName(),
		currentState.getDocumentId(), publishedOn, new AsyncCallbackSimple<Object>() {
		    public void onSuccess(final Object result) {
			Site.hideProgress();
			entityTitle.setContentDate(publishedOn);
		    }
		});

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
	final StateDTO currentState = session.getCurrentState();
	contentServiceProvider.get().setTags(session.getUserHash(), currentState.getGroup().getShortName(),
		currentState.getDocumentId(), tagsString, new AsyncCallbackSimple<List<TagResultDTO>>() {
		    public void onSuccess(final List<TagResultDTO> result) {
			tagsSummaryProvider.get().setGroupTags(result);
			Site.hideProgress();
		    }
		});
    }

}
