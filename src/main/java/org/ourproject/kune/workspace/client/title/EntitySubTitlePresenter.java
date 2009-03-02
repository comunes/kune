/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
import org.ourproject.kune.platf.client.i18n.I18nUITranslationService;
import org.ourproject.kune.platf.client.registry.AuthorableRegistry;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.suco.client.events.Listener;

public class EntitySubTitlePresenter implements EntitySubTitle {

    private EntitySubTitleView view;
    private final I18nUITranslationService i18n;
    private final boolean showLanguage;
    private final AuthorableRegistry authorableRegistry;

    public EntitySubTitlePresenter(final I18nUITranslationService i18n, final StateManager stateManager,
            boolean showLanguage, AuthorableRegistry authorableRegistry) {
        this.i18n = i18n;
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
            String dateFormated = i18n.formatDateWithLocale(publishedOn);
            view.setContentSubTitleRight(i18n.t("Published on: [%s]", dateFormated));
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
