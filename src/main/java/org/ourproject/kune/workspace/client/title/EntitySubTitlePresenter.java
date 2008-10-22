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

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;

public class EntitySubTitlePresenter implements EntitySubTitle {

    private EntitySubTitleView view;
    private final I18nTranslationService i18n;
    private final boolean showLanguage;

    public EntitySubTitlePresenter(final I18nTranslationService i18n, final StateManager stateManager,
            boolean showLanguage, KuneErrorHandler errorHandler) {
        this.i18n = i18n;
        this.showLanguage = showLanguage;
        stateManager.onStateChanged(new Listener<StateDTO>() {
            public void onEvent(final StateDTO state) {
                setState(state);
            }
        });
        errorHandler.onNotDefaultContent(new Listener0() {
            public void onEvent() {
                view.setContentSubTitleLeftVisible(false);
                view.setContentSubTitleRightVisible(false);
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(final EntitySubTitleView view) {
        this.view = view;
    }

    public void setContentLanguage(final String langName) {
        if (showLanguage) {
            view.setContentSubTitleRight(i18n.t("Language: [%s]", langName));
        }
    }

    private void setState(final StateDTO state) {
        if (state.hasDocument()) {
            view.setContentSubTitleLeft(i18n.tWithNT("by: [%s]", "used in a list of authors",
                    state.getAuthors().get(0).getName()));
            view.setContentSubTitleLeftVisible(true);
        } else {
            view.setContentSubTitleLeftVisible(false);
        }
        if (state.getLanguage() != null && showLanguage) {
            final String langName = state.getLanguage().getEnglishName();
            setContentLanguage(langName);
            view.setContentSubTitleRightVisible(true);
        } else {
            view.setContentSubTitleRightVisible(false);
        }
    }

}
