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
package org.ourproject.kune.workspace.client.cxt;

import java.util.Date;
import java.util.List;

import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.dto.TagCloudResultDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.tags.TagsSummary;
import org.ourproject.kune.workspace.client.title.EntitySubTitle;

import com.calclab.suco.client.ioc.Provider;

public class ContextPropEditorPresenter implements ContextPropEditor {

    private ContextPropEditorView view;
    private final Session session;
    private final Provider<TagsSummary> tagsSummaryProvider;
    private final Provider<ContentServiceAsync> contentServiceProvider;
    private final EntitySubTitle entitySubTitle;
    private final StateManager stateManager;
    private final ContentCapabilitiesRegistry capabilitiesRegistry;

    public ContextPropEditorPresenter(final Session session, final StateManager stateManager,
            ContentCapabilitiesRegistry capabilitiesRegistry, final Provider<TagsSummary> tagsSummaryProvider,
            final Provider<ContentServiceAsync> contentServiceProvider, final EntitySubTitle entitySubTitle) {
        this.session = session;
        this.stateManager = stateManager;
        this.capabilitiesRegistry = capabilitiesRegistry;
        this.tagsSummaryProvider = tagsSummaryProvider;
        this.contentServiceProvider = contentServiceProvider;
        this.entitySubTitle = entitySubTitle;
    }

    public void addAuthor(final String authorShortName) {
        Site.showProgressProcessing();
        final StateContainerDTO currentState = session.getContentState();
        contentServiceProvider.get().addAuthor(session.getUserHash(), currentState.getStateToken(), authorShortName,
                new AsyncCallbackSimple<Object>() {
                    public void onSuccess(final Object result) {
                        Site.hideProgress();
                        stateManager.reload();
                    }
                });
    }

    public void attach() {
        view.attach();
    }

    public void clear() {
        view.reset();
    }

    public void delAuthor(final String authorShortName) {
        Site.showProgressProcessing();
        final StateContainerDTO currentState = session.getContentState();
        contentServiceProvider.get().removeAuthor(session.getUserHash(), currentState.getStateToken(), authorShortName,
                new AsyncCallbackSimple<Object>() {
                    public void onSuccess(final Object result) {
                        Site.hideProgress();
                        stateManager.reload();
                    }
                });
    }

    public void detach() {
        view.detach();
    }

    public void doChangeLanguage(final String langCode) {
        Site.showProgressProcessing();
        final StateContainerDTO currentState = session.getContentState();
        contentServiceProvider.get().setLanguage(session.getUserHash(), currentState.getStateToken(), langCode,
                new AsyncCallbackSimple<I18nLanguageDTO>() {
                    public void onSuccess(final I18nLanguageDTO lang) {
                        Site.hideProgress();
                    }
                });
    }

    public void init(final ContextPropEditorView view) {
        this.view = view;
    }

    public void setPublishedOn(final Date publishedOn) {
        Site.showProgressProcessing();
        final StateContainerDTO currentState = session.getContentState();
        contentServiceProvider.get().setPublishedOn(session.getUserHash(), currentState.getStateToken(), publishedOn,
                new AsyncCallbackSimple<Object>() {
                    public void onSuccess(final Object result) {
                        Site.hideProgress();
                        entitySubTitle.setContentDate(publishedOn);
                    }
                });
    }

    public void setState(final StateContentDTO state) {
        // In the future check the use of these components by each tool
        final I18nLanguageDTO language = state.getLanguage();
        final AccessListsDTO accessLists = state.getAccessLists();
        final Date publishedOn = state.getPublishedOn();
        final String tags = state.getTags();
        final List<UserSimpleDTO> authors = state.getAuthors();

        String typeId = state.getTypeId();
        if (capabilitiesRegistry.isTranslatable(typeId)) {
            assert (language != null);
            view.setLanguage(language);
        } else {
            view.removeLangComponent();
        }
        if (capabilitiesRegistry.isTageable(typeId)) {
            assert (tags != null);
            view.setTags(tags);
        } else {
            view.removeTagsComponent();
        }
        if (capabilitiesRegistry.isAuthorable(typeId)) {
            assert (authors != null);
            view.setAuthors(authors);
        } else {
            view.removeAuthorsComponent();
        }
        if (capabilitiesRegistry.isPublishModerable(typeId)) {
            if (publishedOn != null) {
                String dateFormat = session.getCurrentLanguage().getDateFormatShort();
                if (dateFormat != null) {
                    dateFormat = dateFormat.replace("yyyy", "Y");
                    dateFormat = dateFormat.replace("yy", "y");
                    dateFormat = dateFormat.replace("MM", "m");
                    dateFormat = dateFormat.replace("M", "n");
                    dateFormat = dateFormat.replace("dd", "xxx");
                    dateFormat = dateFormat.replace("d", "j");
                    dateFormat = dateFormat.replace("xxx", "d");
                    view.setPublishedOn(publishedOn, dateFormat);
                } else {
                    view.setPublishedOn(publishedOn, "M/d/yy def");
                }
            }
        } else {
            view.removePublishedOnComponent();
        }
        if (capabilitiesRegistry.isAclEditable(typeId)) {
            assert (accessLists != null);
            view.setAccessLists(accessLists);
        } else {
            view.removeAccessListComponent();
        }
    }

    public void setTags(final String tagsString) {
        Site.showProgressProcessing();
        final StateContainerDTO currentState = session.getContentState();
        contentServiceProvider.get().setTags(session.getUserHash(), currentState.getStateToken(), tagsString,
                new AsyncCallbackSimple<TagCloudResultDTO>() {
                    public void onSuccess(final TagCloudResultDTO result) {
                        tagsSummaryProvider.get().setGroupTags(result);
                        Site.hideProgress();
                    }
                });
    }

}
