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

import java.util.Date;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ContentIconsRegistry;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.registry.RenamableRegistry;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class EntityTitlePresenter implements EntityTitle {

    private EntityTitleView view;
    private final I18nTranslationService i18n;
    private final KuneErrorHandler errorHandler;
    private final Provider<ContentServiceAsync> contentServiceProvider;
    private final Session session;
    private final Provider<ContextNavigator> contextNavigatorProvider;
    private final ContentIconsRegistry iconsRegistry;
    private final RenamableRegistry renamableContentRegistry;

    public EntityTitlePresenter(final I18nTranslationService i18n, final KuneErrorHandler errorHandler,
            final StateManager stateManager, final Session session,
            final Provider<ContentServiceAsync> contentServiceProvider,
            final Provider<ContextNavigator> contextNavigatorProvider, final ContentIconsRegistry iconsRegistry,
            RenamableRegistry renamableContentRegistry) {
        this.i18n = i18n;
        this.errorHandler = errorHandler;
        this.session = session;
        this.contentServiceProvider = contentServiceProvider;
        this.contextNavigatorProvider = contextNavigatorProvider;
        this.iconsRegistry = iconsRegistry;
        this.renamableContentRegistry = renamableContentRegistry;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                if (state instanceof StateContentDTO) {
                    setState((StateContentDTO) state);
                } else if (state instanceof StateContainerDTO) {
                    setState((StateContainerDTO) state);
                } else {
                    view.setContentIconVisible(false);
                    view.setContentTitleVisible(false);
                    view.setDateVisible(false);
                }
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(final EntityTitleView view) {
        this.view = view;
    }

    public void setContentDate(final Date publishedOn) {
        if (publishedOn != null) {
            String dateFormat = session.getCurrentLanguage().getDateFormat();
            final DateTimeFormat fmt;
            if (dateFormat == null) {
                fmt = DateTimeFormat.getFormat("M/d/yyyy h:mm a");
            } else {
                String abrevMonthInEnglish = DateTimeFormat.getFormat("MMM").format(publishedOn);
                String monthToTranslate = abrevMonthInEnglish + " [%NT abbreviated month]";
                dateFormat = dateFormat.replaceFirst("MMM", "'" + i18n.t(monthToTranslate) + "'");
                fmt = DateTimeFormat.getFormat(dateFormat + " h:mm a");
            }
            view.setContentDate(i18n.t("Published on: [%s]", fmt.format(publishedOn)));
            setContentDateVisible(true);
        } else {
            setContentDateVisible(false);
        }
    }

    /**
     * Used renaming from context
     */
    public void setContentTitle(final String title) {
        view.setContentTitle(title);
        view.setContentTitleVisible(true);
    }

    protected void onTitleRename(final String newName) {
        Site.showProgressSaving();
        final StateToken stateToken = session.getCurrentState().getStateToken();
        final AsyncCallback<String> asyncCallback = new AsyncCallback<String>() {
            public void onFailure(final Throwable caught) {
                view.restoreOldTitle();
                errorHandler.process(caught);
            }

            public void onSuccess(final String result) {
                Site.hideProgress();
                view.setContentTitle(result);
                contextNavigatorProvider.get().setItemText(stateToken, newName);
            }
        };
        if (stateToken.isComplete()) {
            contentServiceProvider.get().renameContent(session.getUserHash(), stateToken, newName, asyncCallback);
        } else {
            contentServiceProvider.get().renameContainer(session.getUserHash(), stateToken, newName, asyncCallback);
        }
        Site.hideProgress();
    }

    private void setContentDateVisible(final boolean visible) {
        view.setDateVisible(visible);
    }

    private void setContentTitle(final String title, final boolean editable) {
        setContentTitle(title);
        view.setContentTitleEditable(editable);
        view.setContentTitleVisible(true);
    }

    private void setIcon(final String contentTypeIcon) {
        if (contentTypeIcon.length() > 0) {
            view.setContentIcon(contentTypeIcon);
            view.setContentIconVisible(true);
        } else {
            view.setContentIconVisible(false);
        }
    }

    private void setState(final StateContainerDTO state) {
        setContentTitle(state.getTitle(), state.getContainerRights().isEditable()
                && renamableContentRegistry.contains(state.getContainer().getTypeId()));
        setContentDateVisible(false);
        final String contentTypeIcon = iconsRegistry.getContentTypeIcon(state.getTypeId(), null);
        setIcon(contentTypeIcon);
        view.setContentTitleVisible(true);
    }

    private void setState(final StateContentDTO state) {
        setContentTitle(state.getTitle(), state.getContentRights().isEditable()
                && renamableContentRegistry.contains(state.getTypeId()));
        Date publishedOn = state.getPublishedOn();
        setContentDate(publishedOn);
        final String contentTypeIcon = iconsRegistry.getContentTypeIcon(state.getTypeId(), state.getMimeType());
        setIcon(contentTypeIcon);
        view.setContentTitleVisible(true);
    }

}
