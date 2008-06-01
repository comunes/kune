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

package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;

public class ContentSubTitlePresenter implements ContentSubTitleComponent {

    private ContentSubTitleView view;
    private final I18nTranslationService i18n;

    public ContentSubTitlePresenter(final I18nTranslationService i18n) {
        this.i18n = i18n;
    }

    public View getView() {
        return view;
    }

    public void init(final ContentSubTitleView view) {
        this.view = view;
    }

    public void setContentLanguage(final String langName) {
        view.setContentSubTitleRight(i18n.t("Language: [%s]", langName));
    }

    public void setState(final StateDTO state) {
        if (state.hasDocument()) {
            view.setContentSubTitleLeft(i18n.tWithNT("by: [%s]", "used in a list of authors", state.getAuthors().get(0)
                    .getName()));
            view.setContentSubTitleLeftVisible(true);
        } else {
            view.setContentSubTitleLeftVisible(false);
        }
        if (state.getLanguage() != null) {
            String langName = state.getLanguage().getEnglishName();
            setContentLanguage(langName);
            view.setContentSubTitleRightVisible(true);
        } else {
            view.setContentSubTitleRightVisible(false);
        }
    }

}
