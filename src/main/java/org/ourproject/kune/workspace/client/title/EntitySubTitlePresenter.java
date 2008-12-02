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
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.registry.AuthorableRegistry;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.suco.client.listener.Listener;
import com.google.gwt.i18n.client.DateTimeFormat;

public class EntitySubTitlePresenter implements EntitySubTitle {

    private EntitySubTitleView view;
    private final I18nTranslationService i18n;
    private final boolean showLanguage;
    private final Session session;
    private final AuthorableRegistry authorableRegistry;

    public EntitySubTitlePresenter(final I18nTranslationService i18n, final StateManager stateManager, Session session,
            boolean showLanguage, AuthorableRegistry authorableRegistry) {
        this.i18n = i18n;
        this.session = session;
        this.showLanguage = showLanguage;
        this.authorableRegistry = authorableRegistry;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                if (state instanceof StateContentDTO) {
                    setState((StateContentDTO) state);
                } else if (state instanceof StateContainerDTO) {
                    setState((StateContainerDTO) state);
                } else {
                    view.setContentSubTitleLeftVisible(false);
                    view.setContentSubTitleRightVisible(false);
                }
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(final EntitySubTitleView view) {
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
            view.setContentSubTitleRight(i18n.t("Published on: [%s]", fmt.format(publishedOn)));
            setContentDateVisible(true);
        } else {
            setContentDateVisible(false);
        }
    }

    public void setContentLanguage(final String langName) {
        if (showLanguage) {
            view.setContentSubTitleRight(i18n.t("Language: [%s]", langName));
        }
    }

    private void setContentDateVisible(final boolean visible) {
        view.setContentSubTitleRightVisible(visible);
    }

    private void setLanguage(final StateContainerDTO state) {
        if (state.getLanguage() != null && showLanguage) {
            final String langName = state.getLanguage().getEnglishName();
            setContentLanguage(langName);
            view.setContentSubTitleRightVisible(true);
        } else {
            view.setContentSubTitleRightVisible(false);
        }
    }

    private void setState(final StateContainerDTO state) {
        view.setContentSubTitleLeftVisible(false);
        setLanguage(state);
        setContentDateVisible(false);
    }

    private void setState(final StateContentDTO state) {
        if (authorableRegistry.contains(state.getTypeId())) {
            view.setContentSubTitleLeft(i18n.tWithNT("by: [%s]", "used in a list of authors",
                    state.getAuthors().get(0).getName()));
            view.setContentSubTitleLeftVisible(true);
        } else {
            view.setContentSubTitleLeftVisible(false);
        }
        setLanguage(state);
        Date publishedOn = state.getPublishedOn();
        setContentDate(publishedOn);
    }

}
